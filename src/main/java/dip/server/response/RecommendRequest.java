package dip.server.response;

import lombok.Data;

import java.io.Serializable;
@Data
public class RecommendRequest implements Serializable {
    long id_car;
    String name;
    String year;
    String bid;
    String image;

}
