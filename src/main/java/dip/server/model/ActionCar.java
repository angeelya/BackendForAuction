package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "action_car")
public class ActionCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_action")
    long id_action;
    @Column(name="id_car")
    long id_car;
    @Column(name="id_user")
    long id_user;
    @Column(name = "coefficient")
    double coefficient;
}
