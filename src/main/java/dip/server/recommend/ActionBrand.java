package dip.server.recommend;

import dip.server.model.ActionCar;
import lombok.Data;

@Data
public class ActionBrand {
    long id_user;
    double sum_coefficient;
    long id_brand;
}
