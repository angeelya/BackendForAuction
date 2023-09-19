package dip.server.repository;

import dip.server.model.BidConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidConfRepository extends JpaRepository<BidConf,Long> {
    @Query(value = "select * from bid_conf where bid=:bid",nativeQuery = true)
    BidConf findBidConf(@Param("bid") String bid);
    @Query(value = "select b.bid from bid_conf b ", nativeQuery = true)
    List<String> findAllName();
}
