package dip.server.response;

import lombok.Data;

import java.util.List;
@Data
public class CarAddRequest {
    long id_car;
    long id_typecar;
    long id_model;
    String reserve_price;
    String year;
    String vin;
    String color;
    String fuel;
    long mileage;
    String document;
    String damage;
    String engine_type;
    long cylinders;
    String drive;
    String type_body;
    String transmission;
    String keys_car;
    String min_bid;
    List<ImageCar> images;




}
