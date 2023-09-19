package dip.server.repository;

import dip.server.model.Bid;
import dip.server.model.MinBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinBidRepository extends JpaRepository<MinBid,Long> {
    @Query(value = "select * from min_bid where id_car=:id_car",nativeQuery = true)
    MinBid findBid(@Param("id_car")long id_car);
}
