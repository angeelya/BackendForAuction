package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="auccar")
public class AucCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_auccar")
    long id_auccar;
    @Column(name="id_car")
    long id_car;
    @Column(name="id_auction")
    long id_auction;
}
