package dip.server.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "query")
public class QueryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_query")
    long id_query;
    @Column(name="id_car")
    long id_car;
    @Column(name="id_user")
    long id_user;
    @Column(name="price")
    double price;
}
