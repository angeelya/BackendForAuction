package dip.server.repository;

import dip.server.model.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyRepository extends JpaRepository<Buy,Long> {
    @Query(value = "select count(*) from buy where id_user=:id_user",nativeQuery = true)
    int findBuyContCar(@Param("id_user") long id_user);
    @Query(value="select *from buy where id_user=:id_user ",nativeQuery = true)
    List<Buy> findBuyById_user(@Param("id_user") long id_user);

}
