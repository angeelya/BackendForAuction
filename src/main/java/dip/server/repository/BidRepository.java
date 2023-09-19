package dip.server.repository;

import dip.server.model.Bid;
import dip.server.model.LocationAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid,Long> {
    @Query(value = "select * from bid where id_user=:id_user order by id_bid desc ",nativeQuery = true)
    List<Bid> findBid(@Param("id_user")long id_user);
    @Query(value = "select * from bid where id_user=:id_user and id_car=:id_car",nativeQuery = true)
    Bid findBidCarUser(@Param("id_user")long id_user,@Param("id_car") long id_car);

    @Transactional
    @Modifying
    @Query(value = "update bid b set  b.price=:price where b.id_user =:id_user and id_car=:id_car ", nativeQuery = true)
    int updateBid(@Param("id_user")long id_user,@Param("id_car") long id_car,@Param("price") long price);
    @Query(value = "select * from bid b where id_car=:id_car order by b.price desc limit 1",nativeQuery = true)
    Bid findMaxBid(@Param("id_car")long id_car);
}
