package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "buy")
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_buy")
    long id_buy;
    @Column(name="id_car")
    long id_car;
    @Column(name="id_user")
    long id_user;
    @Column(name="fullprice")
    double fullprice;
    @Column(name="auctionfee")
    double auctionfee;
    @Column(name="delivery")
    double delivery;
}
