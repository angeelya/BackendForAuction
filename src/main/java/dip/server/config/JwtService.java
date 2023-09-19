package dip.server.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService {
    private String SECRET_KEY="294A404E635266556A586E3272357538782F413F4428472B4B6150645367566B";
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    public <T>  T extractClaim(String token, Function<Claims,T>claimsResolver){
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }
    public Map<String, String> generateToken(UserDetails userDetails){

        return generateToken(new HashMap<>(), userDetails);
    }
    public Map<String, String> generateToken(Map<String,Object>extraClaims, UserDetails userDetails){
        String access_token = Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        String refresh_token = Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(Date.from(LocalDate.now().plusDays(45).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        Map<String,String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        return tokens;
    }
    public boolean isTokenValid(String token, UserDetails userDetails)
    {
     final String username = extractUsername(token);
     return (username.equals(userDetails.getUsername()));
    }


    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
