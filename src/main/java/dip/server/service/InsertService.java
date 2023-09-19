package dip.server.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dip.server.calculation.Calculation;
import dip.server.model.*;
import dip.server.recommend.ActionService;
import dip.server.repository.*;
import dip.server.response.AuctionRequestAdd;
import dip.server.response.CarAddRequest;
import dip.server.response.CarId;
import dip.server.response.ImageCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class InsertService {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AucCarRepository aucCarRepository;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    Calculation calculation;
    @Autowired
    CarRepository carRepository;
    @Autowired
    BuyRepository buyRepository;
    @Autowired
    ReservePriceRepository reservePriceRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
     FavoriteRepository favoriteRepository;
    @Autowired
    ActionService actionService;
    @Autowired
     UsersRepository usersRepository;
    @Autowired
            EmailSendService emailSendService;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    ModelRepository modelRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    UnapprovedRepository unapprovedRepository;
    @Autowired
    MinBidRepository minBidRepository;

    public String insertAuction(AuctionRequestAdd auctionRequestAdd) {
        Auction auction = new Auction();
        String message="true";
        auction.setId_location(auctionRequestAdd.getId_location());
        auction.setName(auctionRequestAdd.getName());
        auction.setDate_auction(auctionRequestAdd.getDate_auction());
        auction.setBid_increase(Long.valueOf(auctionRequestAdd.getBid_increase()));
        auction.setStatus(String.valueOf(StatusAuction.FUTURE));
       try {
           auctionRepository.save(auction);
       }
        catch (Exception e)
        {
            message="false";
        }
        List<AucCar> aucCars = new ArrayList<>();
        for(int position =0; position<auctionRequestAdd.getCarIdList().size();position++)
        {
            CarId carId = auctionRequestAdd.getCarIdList().get(position);
            AucCar aucCar = new AucCar();
            aucCar.setId_car(Long.parseLong(carId.getId_car()));
            aucCar.setId_auction(auctionRepository.findLastId_Auction());
            aucCars.add(aucCar);
        }
        try {
            aucCarRepository.saveAll(aucCars);
        }
        catch (Exception e)
        {
            message="false";
        }
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);    }

    public String insertBid(Map<String, String> map) {
        String message="true";
        if(bidRepository.findBidCarUser(Long.valueOf(map.get("id_user")),Long.valueOf(map.get("id_car")))!=null)
        {
            try {
                bidRepository.updateBid(Long.valueOf(map.get("id_user")), Long.valueOf(map.get("id_car")), Long.valueOf(map.get("price")));
            }
            catch (Exception e)
            {
                message="false";
            }
        }
        else{
            Bid bid = new Bid();
            bid.setId_car(Long.valueOf(map.get("id_car")));
            bid.setId_user(Long.valueOf(map.get("id_user")));
            bid.setPrice(Long.valueOf(map.get("price")));
            try {
                actionService.addActionMakeBid(bid.getId_user(),bid.getId_car());
                bidRepository.save(bid);
            }
            catch (Exception e)
            {
                message="false";
            }
        }
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);    }

    public String insertBuy(Map<String, String> map) {
        String message="true";
        Buy buy = new Buy();
        buy.setId_car(Long.valueOf(map.get("id_car")));
        buy.setId_user(Long.valueOf(map.get("id_user")));
        buy.setAuctionfee(calculation.calculationAuctionFee(Double.valueOf(map.get("price")),Long.valueOf(map.get("id_user")),Long.valueOf(map.get("id_car"))));
        buy.setDelivery(calculation.calculationDelivery(Long.valueOf(map.get("id_car")),Long.valueOf(map.get("id_user"))));
        buy.setFullprice(calculation.calculationFullPrice(Double.valueOf(map.get("price")),buy.getAuctionfee(),buy.getDelivery()));
        try{
            actionService.addActionBuy(buy.getId_user(),buy.getId_car());
            buyRepository.save(buy);
            emailSendService.sendSimpleEmail(usersRepository.findEmail(buy.getId_user()),makeName(buy.getId_car()),buy.getFullprice(),buy.getDelivery(),buy.getAuctionfee());
        }
        catch (Exception e)
        {
            message="false";
        }
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);
    }
    private String makeName(Long id_car) {
        Model model = modelRepository.findModelName(carRepository.findModelCarByCarId(id_car));
        return brandRepository.findBrandName(model.getId_brand())+model.getName();
    }
    public String insertCar(CarAddRequest carAddRequest) {
        String message = "true";
        Car car = new Car();
        car.setType_body(carAddRequest.getType_body());
        car.setColor(carAddRequest.getColor());
        car.setCylinders(carAddRequest.getCylinders());
        car.setDamage(carAddRequest.getDamage());
        car.setDocument(carAddRequest.getDocument());
        car.setDrive(carAddRequest.getDrive());
        car.setEngine_type(carAddRequest.getEngine_type());
        car.setFuel(carAddRequest.getFuel());
        car.setId_model(carAddRequest.getId_model());
        car.setId_typecar(carAddRequest.getId_typecar());
        car.setKeys_car(carAddRequest.getKeys_car());
        car.setMileage(carAddRequest.getMileage());
        car.setTransmission(carAddRequest.getTransmission());
        car.setType_body(carAddRequest.getType_body());
        car.setVin(carAddRequest.getVin());
        car.setYear(carAddRequest.getYear());

        try{
            Car carP =carRepository.save(car);
            ReservePrice reservePrice = new ReservePrice();
            reservePrice.setId_car(carP.getId_car());
            reservePrice.setRes_price(Long.valueOf(carAddRequest.getReserve_price()));
            reservePriceRepository.save(reservePrice);
            MinBid minBid = new MinBid();
            minBid.setId_car(carP.getId_car());
            minBid.setPrice(Long.valueOf(carAddRequest.getMin_bid()));
            minBidRepository.save(minBid);
            saveImage(carAddRequest.getImages(),carP.getId_car());
        }
        catch (Exception e)
        {
            message="false";
        }

        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);
    }

    private void saveImage(List<ImageCar> images, long id_car) throws IOException {
        List<Image> imageList = new ArrayList<>();
        for(int i=0;i<images.size();i++){
            ImageCar imageCar = images.get(i);
            Image image = new Image();
            image.setId_car(id_car);
            image.setName(imageCar.getName_img());
            image.setPath("C:/Users/Angelina/OneDrive/Рабочий стол/server/image/");
        byte[] imageByte = Base64.getDecoder().decode(imageCar.getImage64base());
        FileOutputStream fileOutputStream = new FileOutputStream(new File(image.getPath() + imageCar.getName_img()));
        fileOutputStream.write(imageByte);
            fileOutputStream.close();
        imageList.add(image);
        }
        imageRepository.saveAll(imageList);
    }

    public String insertFavorite(Map<String, String> map) {
        String message="true";
        Map<String,String> res = new HashMap<>();
        Favorite favorite = new Favorite();
        favorite.setId_car(Long.valueOf(map.get("id_car")));
        favorite.setId_user(Long.valueOf(map.get("id_user")));
        try {
          favorite=  favoriteRepository.save(favorite);
            actionService.addActionFavorite(favorite.getId_car(),favorite.getId_user());
            res.put("id_favorite",String.valueOf(favorite.getId_favorite()));
        }
        catch (Exception e)
        {
            message="false";
        }
        res.put("message",message);
        return gson.toJson(res);
    }

    public String insertUnapproved(Map<String, String> map) {
        String message = "true";
        Unapproved unapproved = new Unapproved();
        unapproved.setId_car(Long.valueOf(map.get("id_car")));
        unapproved.setId_user(Long.valueOf(map.get("is_user")));
        try {
            unapprovedRepository.save(unapproved);
        }
        catch (Exception e)
        {
            message="false";
        }
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        return gson.toJson(res);
    }

    public String insertQuery(Map<String, String> map) {
        return null;
    }
}
