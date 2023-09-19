package dip.server.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id_car")
    long id_car;
    @Column (name="id_typecar")
    long id_typecar;
    @Column (name="id_model")
    long id_model;
    @Column (name="year")
    String year;
    @Column (name="vin")
    String vin;
    @Column (name="color")
    String color;
    @Column (name="fuel")
    String fuel;
    @Column (name="mileage")
    long mileage;
    @Column (name="document")
    String document;
    @Column (name="damage")
    String damage;
    @Column (name="engine_type")
    String engine_type;
    @Column (name="cylinders")
    long cylinders;
    @Column (name="drive")
    String drive;
    @Column (name="type_body")
    String type_body;
    @Column (name="transmission")
    String transmission;
    @Column (name="keys_car")
    String keys_car;

}
