package dip.server.recommend;

import dip.server.model.ActionCar;
import dip.server.model.Recommend;
import dip.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service

public class CollaborativeFilteringService {
  @Autowired
   ActionRepository actionRepository;
    @Autowired
    CarRepository carRepository;
  @Autowired
    ModelRepository modelRepository;
  @Autowired
    BrandRepository brandRepository;

  @Autowired
    RecommendRepository recommendRepository;
    SlopeOne slopeOne = new SlopeOne();
  public void getRecommend(long id_user)
  {
   List<Recommend> recommends= slopeOne.beginSlopeOne(brandRepository.findAllBrandName(),getActionData(),id_user);
   for(int i=0; i<recommends.size();i++)
   {
       Recommend recommend = recommends.get(i);
       if(recommendRepository.findRecommendById_userAndId_brand(id_user,recommend.getId_brand())==null)
           recommendRepository.save(recommend);
       else recommendRepository.updateRecommendCoefficient(recommend.getId_user(),recommend.getCoefficient(),recommend.getId_brand());
   }
  }

   public List<ActionBrand> getActionData()
   {
      List<ActionCar> actionCars = actionRepository.getAllAction();
      List<ActionBrand> actionBrands = new ArrayList<>();
      for(int i=0; i< actionCars.size();i++)
      {
       ActionCar actionCar = actionCars.get(i);
       ActionBrand actionBrand = new ActionBrand();
       actionBrand.setId_user(actionCar.getId_user());
       actionBrand.setId_brand(makeIdBrand(actionCar.getId_car()));
       actionBrand.setSum_coefficient(makeSumCoefficient(makeIdBrand(actionCar.getId_car()), actionBrand.getId_user()));
       actionBrands.add(actionBrand);
      }
      return actionBrands;
   }

    private double makeSumCoefficient(Long idBrand, long id_user) {
        return actionRepository.getSumCBrandAction(idBrand,id_user)/actionRepository.getCountBrandAction(idBrand,id_user);
    }

    private Long makeIdBrand(Long id_car) {
        return modelRepository.findModelName(carRepository.findModelCarByCarId(id_car)).getId_brand();
    }


}
