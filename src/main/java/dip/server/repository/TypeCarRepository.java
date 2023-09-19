package dip.server.repository;

import dip.server.model.TypeCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeCarRepository extends JpaRepository<TypeCar,Long> {
    @Query(value = "select * from type_car where id_typecar=:id_typecar",nativeQuery = true)
    TypeCar findTypeCar(@Param("id_typecar") long id_typecar);
    @Query(value = "select * from type_car",nativeQuery = true)
    List<TypeCar> findAllTypeCar();
}
