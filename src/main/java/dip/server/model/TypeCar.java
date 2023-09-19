package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="type_car")
public class TypeCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_typecar")
    long id_typecar;
    @Column(name="name")
    String name;
    @Column(name = "coefficient")
    double coefficient;
}
