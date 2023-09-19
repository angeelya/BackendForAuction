package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="location_auction")
public class LocationAuction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_location")
    long id_location;
    @Column(name="location")
    String location;
}
