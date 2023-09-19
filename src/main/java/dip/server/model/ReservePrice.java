package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reserve_price")
public class ReservePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_price")
    long id_price;
    @Column(name="id_car")
    long id_car;
    @Column(name="res_price")
    long res_price;
}
