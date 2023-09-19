package dip.server.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationService service;
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  @PostMapping("/register")
    public String register(
          @RequestBody RegisterRequest request
  ){
  return gson.toJson(service.register(request));
  }
    @PostMapping("/authenticate")
    public String authenticate(
            @RequestBody AuthenticationRequest request
    ){
      return gson.toJson(service.authenticate(request));
    }
  @PostMapping("/refresh")
  public String refreshToken(
          @RequestBody RefreshRequest request
  ){
    return gson.toJson(service.refresh(request.getRefresh_token()));
  }
}
