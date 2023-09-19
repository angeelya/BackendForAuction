package dip.server.repository;

import dip.server.model.Image;
import dip.server.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query(value = "select * from image where id_car=:id_car order by id_img limit 1",nativeQuery = true)
    Image findImageFirst(@Param("id_car") long id_car);
    @Query(value = "select * from image where id_car=:id_car ",nativeQuery = true)
    List<Image> findImageAllCar(@Param("id_car") long id_car);
}
