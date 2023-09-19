package dip.server.repository;

import dip.server.model.ActionCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<ActionCar,Long> {
    @Query(value = "select a.id_car from action_car a where id_user=:id_user order by a.id_action desc",nativeQuery = true)
    List<Long> findIdCarView(@Param("id_user") long id_user);
    @Query(value = "select * from action_car  where id_user=:id_user and id_car=:id_car",nativeQuery = true)
    ActionCar checkAction(@Param("id_user") long id_user,@Param("id_car")long id_car);
    @Query(value = "select * from action_car",nativeQuery = true)
    List<ActionCar> getAllAction();
    @Query(value = "select sum(a.coefficient)from action_car a left join car c on a.id_car=c.id_car left join model m on m.id_model = c.id_model where m.id_brand=:id_brand and a.id_user=:id_user",nativeQuery = true)
    double getCountBrandAction(@Param("id_brand") long id_brand,@Param("id_user") long id_user);
    @Query(value = "select count(m.id_brand) from action_car a left join car c on a.id_car=c.id_car left join model m on m.id_model = c.id_model where m.id_brand=:id_brand and a.id_user=:id_user",nativeQuery = true)
    double getSumCBrandAction(@Param("id_brand") long id_brand,@Param("id_user") long id_user);
    @Transactional
    @Modifying
    @Query(value = "update action_car a set  a.coefficient=:coefficient where a.id_user =:id_user and id_car=:id_car ", nativeQuery = true)
    int updateActionCoefficient(@Param("id_user")long id_user,@Param("id_car") long id_car,@Param("coefficient") double coefficient);

}
