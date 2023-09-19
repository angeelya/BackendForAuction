package dip.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dip.server.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/update")
public class UpdateController {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    UpdateService service;
    @PostMapping("/password")
    public String updatePassword(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.updatePassword(map);
    }
    @PostMapping("/email")
    public String updateEmail(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.updateEmail(map);
    }
    @PostMapping("/location")
    public String updateLocation(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.updateLocation(map);
    }
}
