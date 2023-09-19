package dip.server.response;

import lombok.Data;

@Data
public class CarRequest {
    long id_car;
    String name;
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
    String image;
    String bid;

}
