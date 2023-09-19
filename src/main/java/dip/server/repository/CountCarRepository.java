package dip.server.repository;

import dip.server.model.BidConf;
import dip.server.model.CountCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountCarRepository extends JpaRepository<CountCar,Long> {
    @Query(value = "select * from count_car where count_car=:count_car",nativeQuery = true)
    CountCar findCountConf(@Param("count_car") String count_car);
    @Query(value = "select c.count_car from count_car c ", nativeQuery = true)
    List<String> findAllNameCount();

}
