package dip.server.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "min_bid")
public class MinBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_min")
    long id_min;
    @Column(name="id_car")
    long id_car;
    @Column(name="price")
    long price;
}
