package dip.server.repository;


import dip.server.model.BidConf;
import dip.server.model.YearConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearConfRepository extends JpaRepository<YearConf,Long> {
    @Query(value = "select * from year_conf where year=:year",nativeQuery = true)
    YearConf findYearConf(@Param("year") String year);
    @Query(value = "select y.year from year_conf y ", nativeQuery = true)
    List<String> findAllName();
}
