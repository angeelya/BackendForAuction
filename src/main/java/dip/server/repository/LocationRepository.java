package dip.server.repository;

import dip.server.model.Auction;
import dip.server.model.LocationAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationAuction,Long> {
    @Query(value = "select * from location_auction where id_location=:id_loc",nativeQuery = true)
    LocationAuction findListLocation(@Param("id_loc")long id_location);
    @Query(value = "select * from location_auction ",nativeQuery = true)
    List<LocationAuction> getAllLocation();
}
