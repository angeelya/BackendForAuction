package dip.server.repository;

import dip.server.model.Model;
import org.springframework.boot.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model,Long> {
    @Query(value = "select * from model where id_model=:id_model",nativeQuery = true)
    Model findModelName(@Param("id_model") long id_model);
    @Query(value = "select * from model where id_brand=:id_brand",nativeQuery = true)
    List<Model> getModelById_brand(@Param("id_brand") long id_brand);

}
