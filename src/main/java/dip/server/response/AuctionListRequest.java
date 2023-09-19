package dip.server.response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AuctionListRequest {
    long id_auction;
    String location;
    String name;
    String date_auction;
    long bid_increase;
    String status;
}
