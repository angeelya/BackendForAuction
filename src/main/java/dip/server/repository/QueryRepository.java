package dip.server.repository;

import dip.server.model.LocationAuction;
import dip.server.model.QueryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<QueryModel,Long> {

    @Query(value = "select * from query where id_user=:id_user",nativeQuery = true)
    List<QueryModel> findQueryModelById_user(@Param("id_user")long id_user);
    @Transactional
    @Modifying
    @Query(value = "delete from query where id_query=:id_query",nativeQuery = true)
    void deleteQuery(@Param("id_query") Long id_query);
}
