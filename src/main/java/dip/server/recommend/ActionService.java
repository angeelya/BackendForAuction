package dip.server.recommend;

import dip.server.model.ActionCar;
import dip.server.repository.ActionRepository;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class ActionService {
    @Autowired
    ActionRepository actionRepository;


public void addActionViewUser(long id_user, long id_car)
{   if(actionRepository.checkAction(id_user,id_car)==null)
{
    ActionCar actionCar = new ActionCar();
    actionCar.setId_car(id_car);
    actionCar.setId_user(id_user);
    actionCar.setCoefficient(0.25);
    try{
        actionRepository.save(actionCar);
    }
    catch (Exception e)
    {
        LoggerFactory.getLogger(ActionService.class).info("Не собираются данные");
    }
}
}

    public void addActionFavorite(long id_car, long id_user) {
    if(actionRepository.checkAction(id_user,id_car).getCoefficient()<0.5) {
        try {
            actionRepository.updateActionCoefficient(id_user, id_car, 0.5);
        } catch (Exception e) {
            LoggerFactory.getLogger(ActionService.class).info("Не ставятся коэффициенты избранных");
        }
    }
    }

    public void deleteActionFavorite(long id_user, long id_car) {
        if(actionRepository.checkAction(id_user,id_car).getCoefficient()==0.5){
        try{
            actionRepository.updateActionCoefficient(id_user,id_car,0.25);
        }
        catch (Exception e)
        {
            LoggerFactory.getLogger(ActionService.class).info("Не удаляются коэффициенты избранных");
        }
        }
    }

    public void addActionMakeBid(long id_user, long id_car) {
        if(actionRepository.checkAction(id_user,id_car).getCoefficient()<0.75) {
            try {
                actionRepository.updateActionCoefficient(id_user, id_car, 0.75);
            } catch (Exception e) {
                LoggerFactory.getLogger(ActionService.class).info("Не ставятся коэффициенты ставок");
            }
        }
    }

    public void addActionBuy(long id_user, long id_car) {
        if(actionRepository.checkAction(id_user,id_car).getCoefficient()<1.0) {
            try {
                actionRepository.updateActionCoefficient(id_user, id_car, 1.0);
            } catch (Exception e) {
                LoggerFactory.getLogger(ActionService.class).info("Не ставятся коэффициенты покупок");
            }
        }

    }


}
