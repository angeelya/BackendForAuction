package dip.server.repository;

import dip.server.model.Auction;
import dip.server.response.AuctionListRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository <Auction,Long> {

   @Query(value = "select * from auction order by id_auction desc ",nativeQuery = true)
    List<Auction> findListAuction();
   @Query(value = "select id_auction from auction order by id_auction desc limit 1",nativeQuery = true)
    Long findLastId_Auction();
   @Query(value = "select a.date_auction from auccar au join auction a on au.id_auction=a.id_auction where au.id_car=:id_car",nativeQuery = true)
    String findAuctionDataForCar(@Param("id_car") long id_car);
}
