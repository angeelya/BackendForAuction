package dip.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dip.server.service.EmailSendService;
import dip.server.service.SelectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/select")
public class SelectController {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    SelectService service;
    @Autowired
    EmailSendService emailSendService;
    @GetMapping("/auction/list")
    public String getAuctionList()
    {
        return service.getAuctionList();
    }
    @PostMapping("/user/my/bid/user/id")
    public String findCarAndBid(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.findCarAndBid(map);
    }
    @GetMapping("/user/brand")
    public String getBrand()
    {
        return service.getBrand();
    }
    @PostMapping("/user/buy/user/id")
    public String selectBuy(@RequestBody String json)
    {   JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.selectBuy(map);
    }
    @PostMapping("/user/car/auction/id")
    public String findCarByAuctionId(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.findCarByAuctionId(map);
    }
    @PostMapping("/user/car/user/id/view")
    public String findCarByUserIdFromView(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.findCarByUserIdFromView(map);
    }
    @PostMapping("/user/cer/image/car/id")
    public String findCarName(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.findCarName(map);}
    @PostMapping("/user/buy/id/car")
    public String selectCarId(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.selectCarId(map);}
    @PostMapping("/user/search/car")
    public String searchCar(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.searchCar(map);}
    @PostMapping("/user/info/car")
    public String findInfoCar(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.findInfoCar(map);
    }
    @GetMapping("/user/car/add/auction")
    public String findCarForAddAuction() throws IOException {
        return service.findCarForAddAuction();
    }
    @PostMapping("/user/car/model/year")
    public String findCarFull(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.findCarFull(map);
    }
    @PostMapping("/user/car/id/car/list/image")
    public String findCarForCar(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.findCarForCar(map);
    }
    @GetMapping("/destination")
    public String getDestination(){
        return service.getDestination();

    }
    @PostMapping("/user/favorite/user/id")
    public String findFavoriteByUserId(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.findFavoriteByUserId(map);
    }
    @PostMapping("/user/favorite/user/id/car")
    public String checkFavorite(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.checkFavorite(map);
    }
    // @PostMapping("/send")
   // public void send(@RequestBody String json){
     //   JsonObject object = new JsonParser().parse(json).getAsJsonObject();
     // emailSendService.sendSimpleEmail  (gson.fromJson(object.get("email"), String.class), "BMW",4444.0, 56.0, 67.0);
    //}
    @GetMapping("/user/location/auction")
    public String findLocationAuction(){
        return service.findLocationAuction();

    }
    @PostMapping("/user/model/brand")
    public String getModel(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.getModel(map);
    }
    @PostMapping("/user/query/user/id")
    public String findQueryByUserId(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.findQueryByUserId(map);
    }
    @PostMapping("/user/recommend/car")
    public String getRecommend(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.getRecommend(map);
    }
    @PostMapping("/user/check/recommend")
    public String checkRecommend(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.checkRecommend(map);
    }
    @GetMapping("/user/type/car")
    public String getTypeCar(){
        return service.getTypeCar();
    }
    @PostMapping("/user/unapproved/user/id")
    public String selectUnapproved(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.selectUnapproved(map);
    }
    @PostMapping("/user/data")
    public String getDataUser(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.getDataUser(map);
    }
    @PostMapping("/user/data/real/auction")
    public String getDataAuctionReal(@RequestBody String json) throws IOException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Map<String,String> map = gson.fromJson(object, Map.class);
        return service.getDataAuctionReal(map);
    }
}
