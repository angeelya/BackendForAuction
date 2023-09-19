package dip.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "recommend")
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_recommend")
    long id_recommend;
    @Column(name="id_user")
    long id_user;
    @Column(name = "id_brand")
    long id_brand;
    @Column(name = "coefficient")
    double coefficient;
}
