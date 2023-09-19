package dip.server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dip.server.model.*;
import dip.server.recommend.ActionService;
import dip.server.recommend.CollaborativeFilteringService;
import dip.server.repository.*;
import dip.server.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class SelectService {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    QueryRepository queryRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ModelRepository modelRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    BuyRepository buyRepository;
    @Autowired
    AucCarRepository aucCarRepository;
    @Autowired
    ActionRepository actionRepository;
    @Autowired
    TypeCarRepository typeCarRepository;
    @Autowired
    ActionService actionService;
    @Autowired
    DestinationRepository destinationRepository;
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    RecommendRepository recommendRepository;
    @Autowired
    UnapprovedRepository unapprovedRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CollaborativeFilteringService collaborativeFilteringService;
    @Autowired
    MinBidRepository minBidRepository;
    public String getAuctionList() {
        List<Auction> auctionList = auctionRepository.findListAuction();
        List<AuctionListRequest>auctionListRequestList = new ArrayList<>();
        for(int position = 0;position<auctionList.size();position++){
            Auction auction =auctionList.get(position);
           LocationAuction locationAuction = locationRepository.findListLocation(auction.getId_location());
            AuctionListRequest auctionListRequest= new AuctionListRequest();
           auctionListRequest.setId_auction(auction.getId_auction());
            auctionListRequest.setLocation(locationAuction.getLocation());
            auctionListRequest.setDate_auction(auction.getDate_auction());
            auctionListRequest.setName(auction.getName());
            auctionListRequest.setBid_increase(auction.getBid_increase());
            auctionListRequest.setStatus(translateStatus(auction.getStatus()));
            auctionListRequestList.add(auctionListRequest);
        }
        return gson.toJson(auctionListRequestList);
    }

    private String translateStatus(String status) {
        Map<String,String> map = new HashMap<>();
        map.put(String.valueOf(StatusAuction.FUTURE),"Будущий");
        map.put(String.valueOf(StatusAuction.NOW),"Сейчас");
        return status=map.get(status);
    }

    public String findCarAndBid(Map<String, String> map) throws IOException {
        List<Bid> bidList = bidRepository.findBid(Long.valueOf(map.get("id_user")));
        List<MyBidRequest> myBidRequestList = new ArrayList<>();
        for(int position=0;position<bidList.size();position++)
        {   Bid bid = bidList.get(position);
            MyBidRequest myBidRequest = new MyBidRequest();
            myBidRequest.setName(makeName(bid.getId_car()));
            myBidRequest.setId_bid(bid.getId_bid());
            myBidRequest.setId_car(bid.getId_car());
            myBidRequest.setId_user(bid.getId_user());
            myBidRequest.setPrice(bid.getPrice());
            myBidRequest.setImage(makeImage64(bid.getId_car()));
            myBidRequestList.add(myBidRequest);
        }
        return gson.toJson(myBidRequestList);
    }

    public String getBrand() {
        return gson.toJson(brandRepository.findBrand());
    }

    public String selectBuy(Map<String, String> map) {
        return gson.toJson(buyRepository.findBuyById_user(Long.valueOf(map.get("id_user"))));
    }

    public String findCarByAuctionId(Map<String, String> map) throws IOException {
        List<Long> carIdList;
        if(map.get("sort").equals("sort"))
        {
            carIdList = aucCarRepository.findIdCarByAuctionIdSort(Long.valueOf(map.get("id_auction")));
        }
        else
        {
            carIdList = aucCarRepository.findIdCarByAuctionIdNoSort(Long.valueOf(map.get("id_auction")));

        }
        return gson.toJson( makeCarRequest(carIdList));
    }

    private List<CarRequest> makeCarRequest(List<Long> carIdList) throws IOException {
        List<CarRequest> carRequests = new ArrayList<>();
    for(int k =0; k<carIdList.size();k++){
            Long carId = carIdList.get(k);
            Car car = carRepository.findCarAuction(carId);
            CarRequest carRequest = new CarRequest();
            carRequest.setId_car(carId);
            carRequest.setName(makeName(Long.valueOf(car.getId_car())));
            carRequest.setYear(car.getYear());
            carRequest.setVin(car.getVin());
            carRequest.setColor(car.getColor());
            carRequest.setFuel(car.getFuel());
            carRequest.setMileage(car.getMileage());
            carRequest.setDocument(car.getDocument());
            carRequest.setDamage(car.getDamage());
            carRequest.setEngine_type(car.getEngine_type());
            carRequest.setCylinders(car.getCylinders());
            carRequest.setDrive(car.getDrive());
            carRequest.setType_body(car.getType_body());
            carRequest.setTransmission(car.getTransmission());
            carRequest.setKeys_car(car.getKeys_car());
            carRequest.setImage(makeImage64(carId));
            if(bidRepository.findMaxBid(carId)!=null)
            carRequest.setBid(String.valueOf(bidRepository.findMaxBid(carId).getPrice()));
            else carRequest.setBid(String.valueOf(minBidRepository.findBid(carId).getPrice()));
            carRequests.add(carRequest);
        }
      return carRequests;
    }

    public String findCarByUserIdFromView(Map<String, String> map) throws IOException {
      return gson.toJson(makeCarRequest(actionRepository.findIdCarView(Long.valueOf(map.get("id_user")))));
    }

    public String findCarName(Map<String, String> map) throws IOException {
        Map<String,String> response = new HashMap<>();
        response.put("name",makeName(Long.valueOf(map.get("id_car"))));
        response.put("year",carRepository.findYearByCarId(Long.valueOf(map.get("id_car"))));
        response.put("bid",String.valueOf(bidRepository.findMaxBid(Long.valueOf(map.get("id_car"))).getPrice()));
        response.put("image",makeImage64(Long.valueOf(map.get("id_car"))));
        return gson.toJson(response);
    }

    public String selectCarId(Map<String, String> map) throws IOException {
        Map<String,String> response = new HashMap<>();
        response.put("name",makeName(Long.valueOf(map.get("id_car"))));
        response.put("image",makeImage64(Long.valueOf(map.get("id_car"))));
        response.put("year",String.valueOf(carRepository.findYearByCarId(Long.valueOf(map.get("id_car")))));
        response.put("bid",String.valueOf(bidRepository.findMaxBid(Long.valueOf(map.get("id_car"))).getPrice()));
        return gson.toJson(response);
    }

    private String makeImage64(Long id_car) throws IOException {
        byte[] imageByte = Files.readAllBytes(new File(imageRepository.findImageFirst(id_car).getPath()+imageRepository.findImageFirst(id_car).getName()).toPath());
         return Base64.getEncoder().encodeToString(imageByte);
    }

    private String makeName(Long id_car) {
        Model model = modelRepository.findModelName(carRepository.findModelCarByCarId(id_car));
        return brandRepository.findBrandName(model.getId_brand())+ " "+model.getName();
    }

    public String searchCar(Map<String, String> map) throws IOException {
        return gson.toJson(makeCarRequest(carRepository.findKeyCarId(map.get("key"))));
    }

    public String findInfoCar(Map<String, String> map) {
        Car car = carRepository.findCarAuction(Long.valueOf(map.get("id_car")));
        CarInfo carInfo = new CarInfo();
        carInfo.setId_car(car.getId_car());
        carInfo.setTypecar(typeCarRepository.findTypeCar(car.getId_typecar()).getName());
        carInfo.setName(makeName(Long.valueOf(map.get("id_car"))));
        carInfo.setYear(car.getYear());
        carInfo.setVin(car.getVin());
        carInfo.setColor(car.getColor());
        carInfo.setFuel(car.getFuel());
        carInfo.setMileage(car.getMileage());
        carInfo.setDocument(car.getDocument());
        carInfo.setDamage(car.getDamage());
        carInfo.setEngine_type(car.getEngine_type());
        carInfo.setCylinders(car.getCylinders());
        carInfo.setDrive(car.getDrive());
        carInfo.setType_body(car.getType_body());
        carInfo.setTransmission(car.getTransmission());
        carInfo.setKeys_car(car.getKeys_car());
        return gson.toJson(carInfo);
    }

    public String findCarForAddAuction() throws IOException {
        return gson.toJson(makeCarAuctionAndFullList(carRepository.findAllNoAuctionCar()));
    }

    private List<CarAuctionAndFull> makeCarAuctionAndFullList(List<Car> carList) throws IOException {
        List<CarAuctionAndFull> carAuctionAndFulls = new ArrayList<>();
        for(int pos=0;pos<carList.size();pos++)
        {
            Car car = carList.get(pos);
            CarAuctionAndFull carAuctionAndFull = new CarAuctionAndFull();
            carAuctionAndFull.setId_car(car.getId_car());
            carAuctionAndFull.setName(makeName(car.getId_car()));
            carAuctionAndFull.setYear(car.getYear());
            carAuctionAndFull.setImage(makeImage64(car.getId_car()));
            carAuctionAndFulls.add(carAuctionAndFull);
        }
        return carAuctionAndFulls;
    }


    public String findCarFull(Map<String, String> map) throws IOException {
     return gson.toJson(makeCarAuctionAndFullList(carRepository.findFullSearch(Long.valueOf(map.get("id_model")),map.get("yearBefore"),map.get("yearAfter"))));
    }

    public String findCarForCar(Map<String, String> map) throws IOException {
     Car car = carRepository.findCarAuction(Long.valueOf(map.get("id_car")));
     CarForCar carForCar = new CarForCar();
     if(auctionRepository.findAuctionDataForCar(car.getId_car())!=null) {
         carForCar.setDate_auction(auctionRepository.findAuctionDataForCar(car.getId_car()));
     }
     else {
         carForCar.setDate_auction("Сыиграно");
     }
     carForCar.setId_car(car.getId_car());
     carForCar.setKeys_car(car.getKeys_car());
     if(bidRepository.findMaxBid(car.getId_car())!=null)
     carForCar.setBid(String.valueOf(bidRepository.findMaxBid(car.getId_car()).getPrice()));
     else carForCar.setBid(String.valueOf(minBidRepository.findBid(car.getId_car()).getPrice()));
     carForCar.setColor(car.getColor());
     carForCar.setType_body(car.getType_body());
     carForCar.setCylinders(car.getCylinders());
     carForCar.setDamage(car.getDamage());
     carForCar.setDocument(car.getDocument());
     carForCar.setDrive(car.getDrive());
     carForCar.setEngine_type(car.getEngine_type());
     carForCar.setFuel(car.getFuel());
     carForCar.setImageList(makeImageList64(car.getId_car()));
     carForCar.setMileage(car.getMileage());
     carForCar.setName(makeName(car.getId_car()));
     carForCar.setTransmission(car.getTransmission());
     carForCar.setTypecar(typeCarRepository.findTypeCar(car.getId_typecar()).getName());
     carForCar.setVin(car.getVin());
     carForCar.setYear(car.getYear());
     actionService.addActionViewUser(Long.valueOf(map.get("id_user")),car.getId_car());
     if(Long.valueOf(map.get("count_view"))>=10)
        collaborativeFilteringService.getRecommend(Long.valueOf(map.get("id_user")));
     return gson.toJson(carForCar);
    }

    private List<String> makeImageList64(long id_car) throws IOException {
        List<Image> imageList = imageRepository.findImageAllCar(id_car);
        List<String> images64= new ArrayList<>();
        for(int i=0; i<imageList.size();i++)
        {
            Image image = imageList.get(i);
            byte[] imageByte = Files.readAllBytes(new File(image.getPath()+image.getName()).toPath());
            images64.add(Base64.getEncoder().encodeToString(imageByte));
        }
        return images64;
    }

    public String getDestination() {
        return gson.toJson(destinationRepository.getDestination());
    }

    public String findFavoriteByUserId(Map<String, String> map) {
        return gson.toJson(favoriteRepository.getFavoriteById_user(Long.valueOf(map.get("id_user"))));
    }

    public String checkFavorite(Map<String, String> map) {
        Map<String,String> res = new HashMap<>();
        String message = "true";
        Favorite favorite =favoriteRepository.checkFavorite(Long.valueOf(map.get("id_user")),Long.valueOf(map.get("id_car")));
        if(favorite==null){
            message="false";
        }
        else {
        res.put("id_favorite",String.valueOf(favorite.getId_favorite()));}
        res.put("message",message);
        return gson.toJson(res);
    }

    public String findLocationAuction() {
        return gson.toJson(locationRepository.getAllLocation());
    }

    public String getModel(Map<String, String> map) {
        return gson.toJson(modelRepository.getModelById_brand(Long.valueOf(map.get("id_brand"))));
    }

    public String findQueryByUserId(Map<String, String> map) {
        return gson.toJson(queryRepository.findQueryModelById_user(Long.valueOf(map.get("id_user"))));
    }

    public String getRecommend(Map<String, String> map) throws IOException {
        List<Recommend> recommends = recommendRepository.findRecommendById_user(Long.valueOf(map.get("id_user")),0.2);
        List<RecommendRequest> recommendRequests = new ArrayList<>();
        for(int i=0; i<recommends.size();i++){
            Recommend recommend = recommends.get(i);
            recommendRequests.addAll(makeRecommendRequestList(recommend.getId_brand()));
        }
        return gson.toJson(recommendRequests);
    }

    private List<RecommendRequest> makeRecommendRequestList(long id_brand) throws IOException {
        List<Long> carId = carRepository.findCarIdByBrandId(id_brand);
        List<RecommendRequest> recommendRequests = new ArrayList<>();
        for(int i =0 ; i<carId.size();i++)
        {
            Long id_car = carId.get(i);
            RecommendRequest recommendRequest = new RecommendRequest();
            recommendRequest.setId_car(id_car);
            recommendRequest.setImage(makeImage64(id_car));
            if(bidRepository.findMaxBid(id_car)!=null)
                recommendRequest.setBid(String.valueOf(bidRepository.findMaxBid(id_car).getPrice()));
            else recommendRequest.setBid(String.valueOf(minBidRepository.findBid(id_car).getPrice()));
            recommendRequest.setYear(carRepository.findYearByCarId(id_car));
            recommendRequest.setName(makeName(id_car));
            recommendRequests.add(recommendRequest);
        }
        return  recommendRequests;
    }

    public String checkRecommend(Map<String, String> map) {
        String message;
        if(!recommendRepository.findRecommendById_user(Long.valueOf(map.get("id_user")),0.2).isEmpty())
        {
            message="true";
        }
        else {
            message="false";
        }
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);
    }

    public String getTypeCar() {
        return gson.toJson(typeCarRepository.findAllTypeCar());
    }

    public String selectUnapproved(Map<String, String> map) {
        return gson.toJson(unapprovedRepository.findUnapprovedById_user(Long.valueOf(map.get("id_user"))));
    }

    public String getDataUser(Map<String, String> map) {
        Users user = usersRepository.findUser(Long.valueOf(map.get("id_user")));
        Map<String,String> res = new HashMap<>();
        res.put("name",user.getName());
        res.put("email",user.getEmail());
        res.put("location",destinationRepository.findDestination(user.getId_destination()).getLocation());
        return gson.toJson(res);
    }

    public String getDataAuctionReal(Map<String, String> map) throws IOException {
        List<AuctionReal> auctionReals = new ArrayList<>();
        List<Long> idCars = aucCarRepository.findIdCarByAuctionIdNoSort(Long.valueOf( map.get("id_auction")));
        for(int i =0; i<idCars.size();i++)
        {
          long id_car=idCars.get(i);
          AuctionReal auctionReal = new AuctionReal();
          auctionReal.setId_car(id_car);
          auctionReal.setName(makeName(id_car));
          auctionReal.setImage(makeImage64(id_car));
          Bid bid =bidRepository.findMaxBid(id_car);
            if(bid!=null) {
                auctionReal.setMaxBid(String.valueOf(bid.getPrice()));
                auctionReal.setName_user(String.valueOf(usersRepository.findUser(bid.getId_user()).getName()));
            }
            else {
                auctionReal.setMaxBid(String.valueOf(minBidRepository.findBid(id_car).getPrice()));
                auctionReal.setName_user("Минимальная ставка");
            }
            Bid ourBid = bidRepository.findBidCarUser(Long.valueOf( map.get("id_user")),id_car);
            if(ourBid!=null)
            {
              auctionReal.setOurBid(String.valueOf(ourBid.getPrice()));
            }
            else {
                auctionReal.setOurBid("Нет ставки");
            }
            auctionReals.add(auctionReal);
        }
        return gson.toJson(auctionReals);
    }
}
