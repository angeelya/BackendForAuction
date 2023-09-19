package dip.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dip.server.response.AuctionRequestAdd;
import dip.server.response.CarAddRequest;
import dip.server.service.InsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/insert")

public class InsertController {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    InsertService service;
    @PostMapping("/auction")
    public String insertAuction(@RequestBody String json)
    {  JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        AuctionRequestAdd auctionRequestAdd = gson.fromJson(object,AuctionRequestAdd.class);
        return service.insertAuction(auctionRequestAdd);
    }
    @PostMapping ("/bid/car/user")
    public String insertBid (@RequestBody String json)
    {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object,Map.class);
        return service.insertBid(map);
    }
    @PostMapping("/user/query/yes")
    public String insertBuy(@RequestBody String json)
    {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object,Map.class);
        return service.insertBuy(map);
    }
    @PostMapping("/car/add")
    public String insertCar(@RequestBody String json)
    {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        CarAddRequest carAddRequest = gson.fromJson(object,CarAddRequest.class);
        return service.insertCar(carAddRequest);
    }
    @PostMapping("/favorite/user/id/car")
    public String insertFavorite(@RequestBody String json)
    {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object,Map.class);
        return service.insertFavorite(map);
    }
    @PostMapping("/user/unapproved/query/no")
    public String insertUnapproved(@RequestBody String json)
    {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object,Map.class);
        return service.insertUnapproved(map);
    }
    @PostMapping("/query")
    public String insertQuery(@RequestBody String json)
    {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object,Map.class);
        return service.insertQuery(map);
    }
}
