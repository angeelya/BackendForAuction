package dip.server.auth;


import dip.server.config.JwtService;
import dip.server.model.Role;
import dip.server.model.Users;
import dip.server.repository.UsersRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public Map<String, String> register(RegisterRequest request) {
         Map<String,String> response = new HashMap<>();
         if(repository.findByName(request.getName())==null){
             var user = Users.builder()
                     .name(request.getName())
                     .email(request.getEmail())
                     .password(passwordEncoder.encode(request.getPassword()))
                     .id_destination(request.getId_destination())
                     .role(Role.USER)
                     .build();
             repository.save(user);
             response.put("message","userNotFound");
             response.putAll(jwtService.generateToken(user));
             response.put("id_user",String.valueOf(user.getId_user()));
         }
         else{
             response.put("message","userIsExists");
             }
        return response;
    }

    public Map<String,String> authenticate(AuthenticationRequest request) {



        Map<String,String> response = new HashMap<>();

        var user  = repository.findByName(request.getName());
        if(user==null){
            response.put("message","notFound");
        }
        else{
           if(authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getName(),request.getPassword())).isAuthenticated())
            {
                response.put("role",user.getRole().name());
                response.put("message","correct");
                response.putAll(jwtService.generateToken(user));
                response.put("id_user",String.valueOf(user.getId_user()));}
            else{
                response.put("message","incorrect");
            }
        }
        return response;
    }

    public Map<String, String> refresh(String refreshToken) {
        Map<String,String> response = new HashMap<>();
        try{
        var user  = repository.findByName(new JwtService().extractUsername(refreshToken));
        response = jwtService.generateToken(user);
        response.put("message","ok");}
        catch (ExpiredJwtException ex) {

            response.put("message","expired refreshToken");
        }
        return response;
        }

    }

