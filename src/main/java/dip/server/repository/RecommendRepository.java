package dip.server.repository;

import dip.server.model.QueryModel;
import dip.server.model.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend,Long> {
    @Query(value = "select * from recommend where id_user=:id_user and coefficient>:coefficient",nativeQuery = true)
    List<Recommend> findRecommendById_user(@Param("id_user")long id_user,@Param("coefficient") double coefficient);
    @Query(value = "select * from recommend where id_user=:id_user and id_brand=:id_brand",nativeQuery = true)
    Recommend findRecommendById_userAndId_brand(@Param("id_user")long id_user,@Param("id_brand") long id_brand);
    @Transactional
    @Modifying
    @Query(value = "update recommend a set  a.coefficient=:coefficient where a.id_user =:id_user and id_brand=:id_brand ", nativeQuery = true)
    int updateRecommendCoefficient(@Param("id_user")long id_user,@Param("coefficient") double coefficient,@Param("id_brand") long id_brand);

}
