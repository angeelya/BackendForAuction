package dip.server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dip.server.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String encodePass(String pass){
        return passwordEncoder.encode(pass);
    }
    public Boolean encodePassword(String password, Long id_user){
        if(passwordEncoder.matches(password,usersRepository.findUser(id_user).getPassword()))
        {return true;}
        return false;
    }
    public String updatePassword(Map<String, String> map) {
        Map<String, String> res = new HashMap<>();
        if (encodePassword(map.get("old_password"),Long.valueOf(map.get("id_user")))==false) {
            map.put("message", "noOldPassword");
        } else {
            try {
                usersRepository.updatePassword(Long.valueOf(map.get("id_user")), encodePass(map.get("new_password")));
                map.put("message", "true");
            }
            catch (Exception e)
            {
                map.put("message", "false");
            }
        }
        return gson.toJson(map);
    }

    public String updateEmail(Map<String, String> map) {
        String message ="true";
        try {
            usersRepository.updateEmail(Long.valueOf(map.get("id_user")),map.get("email"));
        }
        catch (Exception e){
            message ="false";
        }
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);
    }

    public String updateLocation(Map<String, String> map) {
        String message = "true";
        try {
        usersRepository.updateLocation(Long.valueOf(map.get("id_user")),Long.valueOf(map.get("id_destination")));
        }
        catch (Exception e){
            message ="false";
        }
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);
    }
}
