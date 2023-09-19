package dip.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dip.server.repository.FavoriteRepository;
import dip.server.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/delete")
public class DeleteController {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    DeleteService deleteService;
    @DeleteMapping("/user/favorite/id")
    public String deleteFavorite(@RequestBody String json)
    {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return deleteService.deleteFavorite(map);
    }
    @DeleteMapping("/user/query/id")
    public String deleteQuery(@RequestBody String json)
    {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return deleteService.deleteQuery(map);
    }
}
