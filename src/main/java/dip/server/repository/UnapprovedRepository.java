package dip.server.repository;

import dip.server.model.Recommend;
import dip.server.model.Unapproved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnapprovedRepository extends JpaRepository<Unapproved,Long> {
    @Query(value = "select * from unapproved where id_user=:id_user",nativeQuery = true)
    List<Unapproved> findUnapprovedById_user(@Param("id_user")long id_user);
}
