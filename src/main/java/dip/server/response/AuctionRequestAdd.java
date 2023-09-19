package dip.server.response;

import lombok.Data;

import java.util.List;
@Data
public class AuctionRequestAdd {
    long id_location;
    String date_auction;
    String name;
    String bid_increase;
    List<CarId> carIdList;
}
