package dip.server.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="unapproved")
public class Unapproved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unapproved")
    long id_unapproved;
    @Column(name="id_car")
    long id_car;
    @Column(name="id_user")
    long id_user;
}
