package dip.server.repository;

import dip.server.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    @Query(value = "select  id_model from car where id_car=:id_car",nativeQuery = true)
    Long findModelCarByCarId(@Param("id_car") long id_car);
    @Query(value = "select  year from car where id_car=:id_car",nativeQuery = true)
    String findYearByCarId(@Param("id_car") long id_car);
    @Query(value = "select  id_typecar from car where id_car=:id_car",nativeQuery = true)
    Long findTypeCarByCarId(@Param("id_car") long id_car);
    @Query(value = "select * from car where id_car=:id_car",nativeQuery = true)
    Car findCarAuction(@Param("id_car") long id_car);
    @Query(value = "select c.id_car from car c join model m on c.id_model=m.id_model join brand b on b.id_brand=m.id_brand join auccar au on au.id_car=c.id_car where b.name like %:key% or m.name like %:key% or c.year like %:key%",nativeQuery = true)
    List<Long> findKeyCarId(@Param("key") String key);
    @Query(value = "select c.id_car,c.year,c.id_model,c.color,c.cylinders,c.id_typecar,c.drive,c.damage,c.vin,c.fuel,c.keys_car,c.document,c.mileage,c.engine_type,c.transmission,c.type_body from car c left join auccar a on c.id_car=a.id_car where a.id_car is null",nativeQuery = true)
    List<Car> findAllNoAuctionCar();
    @Query(value = "select c.id_car,c.year,c.id_model,c.color,c.cylinders,c.id_typecar,c.drive,c.damage,c.vin,c.fuel,c.keys_car,c.document,c.mileage,c.engine_type,c.transmission,c.type_body from car c join auccar a on c.id_car=a.id_car where c.id_model=:id_model and c.year between :yearBefore and :yearAfter",nativeQuery = true)
    List<Car> findFullSearch(@Param("id_model") long id_model, @Param("yearBefore") String yearBefore, @Param("yearAfter") String yearAfter);
    @Query (value = "select c.id_car from car c join model m on m.id_model=c.id_model join brand b on b.id_brand=m.id_brand where b.id_brand=:id_brand",nativeQuery = true)
    List<Long> findCarIdByBrandId(@Param("id_brand") long id_brand);

}
