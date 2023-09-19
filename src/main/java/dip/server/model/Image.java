package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="image")
public class Image {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name="id_img")
 long id_img;
    @Column(name="id_car")
    long id_car;
    @Column(name="name")
    String name;
    @Column(name="path")
    String path;
}
