package dip.server.calculation;

import dip.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Calculation {
    @Autowired
    BidConfRepository bidConfRepository;
    @Autowired
    YearConfRepository yearConfRepository;
    @Autowired
    CountCarRepository countCarRepository;
    @Autowired
    BuyRepository buyRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    TypeCarRepository typeCarRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DestinationRepository destinationRepository;
    int bStr, aStr;
    public Double calculationAuctionFee(Double price, long id_user, Long id_car)
    {
    return (getPriceCoefficient(price)+
     getCountCoefficient(buyRepository.findBuyContCar(id_user))+
     getYearCoefficient(Long.valueOf(carRepository.findYearByCarId(id_car))))/3;
    }

    private Double getYearCoefficient(Long yearByCarId) {
        List<String> yearList=yearConfRepository.findAllName();
        Double year_cof =0.01;
        for(int y= 0;y<yearList.size();y++)
        {
           String year= yearList.get(y);
           if(year.indexOf("-")!=-1)
           {
               sub(year);
               if(bStr<yearByCarId&&yearByCarId<aStr)
               {
                   year_cof=yearConfRepository.findYearConf(year).getCoefficient();
               }
           }
           else {
             year_cof=yearConfRepository.findYearConf(year).getCoefficient();
           }
        }
        return year_cof;
    }

    private Double getCountCoefficient(int buyContCar) {
      List<String> countList = countCarRepository.findAllNameCount();
        Double coefficient_count = 0.1;
        for(int k =0;k<countList.size();k++)
        {
            String count = countList.get(k);
            if(count.indexOf("-")!=-1)
            {
             sub(count);
             if(bStr<buyContCar&&buyContCar<aStr)
             {
                 coefficient_count=countCarRepository.findCountConf(count).getCoefficient();
             }
            }
            else{
                coefficient_count=countCarRepository.findCountConf(count).getCoefficient();
            }
        }
         return coefficient_count;
    }

    private void sub(String str) {
        if(str.indexOf("-")!=-1){
           bStr= Integer.valueOf(str.substring(0,str.indexOf("-")-1));
           aStr = Integer.valueOf(str.substring(str.indexOf("-")+1),str.length());
        }
    }

    private double getPriceCoefficient(Double price) {
        List<String> bidList=bidConfRepository.findAllName();
        Double coefficient = 0.1;
        for(int i=0; i<bidList.size();i++)
        {
            String bid = bidList.get(i);
            if(bid.indexOf("-")!=-1){
            sub(bid);}
            if(bStr<price&&price<aStr)
            {
                coefficient= bidConfRepository.findBidConf(bid).getCoefficient();
            }
            else {
                coefficient= bidConfRepository.findBidConf(bid).getCoefficient();
            }
        }
        return coefficient;
    }

    public double calculationDelivery(Long id_car, Long id_user) {
        return (typeCarRepository.findTypeCar(carRepository.findTypeCarByCarId(id_car)).getCoefficient()+
                destinationRepository.findDestination(usersRepository.findDestination(id_user)).getCoefficient())/2;
    }

    public double calculationFullPrice(Double price, double auctionFee, double delivery) {
        return price+price*auctionFee+price*delivery;
    }
}
