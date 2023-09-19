package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auction")
    long id_auction;
    @Column(name = "id_location")
    long id_location;
    @Column(name = "date_auction")
    String date_auction;
    @Column(name = "name")
    String name;
    @Column(name = "bid_increase")
    long bid_increase;
    @Column(name = "status")
    String status;
}
