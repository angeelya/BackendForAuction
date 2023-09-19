package dip.server.repository;

import dip.server.model.AucCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AucCarRepository extends JpaRepository<AucCar,Long> {
    @Query(value = "select a.id_car from auccar a join car c on a.id_car=c.id_car join model m on m.id_model=c.id_model join brand b on b.id_brand=m.id_brand where id_auction=:id_auction order by b.name",nativeQuery = true)
    List<Long> findIdCarByAuctionIdSort(@Param("id_auction") long id_auction);
    @Query(value = "select a.id_car from auccar a where id_auction=:id_auction",nativeQuery = true)
    List<Long> findIdCarByAuctionIdNoSort(@Param("id_auction") long id_auction);
}
