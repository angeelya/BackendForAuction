package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_bid")
    long id_bid;
    @Column(name="id_car")
    long id_car;
    @Column(name="id_user")
    long id_user;
    @Column(name="price")
    long price;
}
