package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "destination")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_destination")
    long id_destination;
    @Column(name="location")
    String location;
    @Column(name="coefficient")
    double coefficient;
}
