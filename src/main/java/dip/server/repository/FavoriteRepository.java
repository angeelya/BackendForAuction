package dip.server.repository;

import dip.server.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
       @Query(value = "select * from favorite where id_user=:id_user",nativeQuery = true)
       List<Favorite> getFavoriteById_user(@Param("id_user") long id_user);
       @Query(value = "select * from favorite where id_user=:id_user and id_car=:id_car",nativeQuery = true)
       Favorite checkFavorite(@Param("id_user") long id_user,@Param("id_car")long id_car);
       @Transactional
       @Modifying
       @Query(value = "delete from favorite where id_favorite=:id_favorite",nativeQuery = true)
       void deleteFavorite(@Param("id_favorite") Long id_favorite);
       @Query(value = "select * from favorite where id_favorite=:id_favorite",nativeQuery = true)
       Favorite getFavorite(@Param("id_favorite") long id_favorite);
}
