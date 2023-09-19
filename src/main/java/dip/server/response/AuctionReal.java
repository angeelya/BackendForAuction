package dip.server.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class AuctionReal implements Serializable {
    long id_car;
    String name_user;
    String name;
    String ourBid;
    String MaxBid;
    String image;

}
