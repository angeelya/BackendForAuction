package dip.server.response;

import lombok.Data;

@Data
public class MyBidRequest {
    long id_bid;
    String name;
    long id_user;
    long price;
    String image;
   long id_car;
}
