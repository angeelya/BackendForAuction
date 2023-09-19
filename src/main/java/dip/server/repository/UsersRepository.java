package dip.server.repository;

import dip.server.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository  extends JpaRepository<Users, Long> {
    @Query (value = "select * from users where name=:name",nativeQuery = true)
    Users findByName(@Param("name") String name);
    @Query(value = "select id_destination from users where id_user=:id_user",nativeQuery = true)
    Long findDestination(@Param("id_user") long id_user);
    @Query(value = "select email from users where id_user=:id_user",nativeQuery = true)
    String findEmail(@Param("id_user") long id_user);
    @Query(value = "select * from users where id_user=:id_user",nativeQuery = true)
    Users findUser(@Param("id_user") long id_user);

    @Transactional
    @Modifying
    @Query(value = "update users u set  u.new_password=:new_password where u.id_user =:id_user ", nativeQuery = true)
    int updatePassword(@Param("id_user")long id_user,@Param("new_password") String new_password);
    @Transactional
    @Modifying
    @Query(value = "update users u set  u.email=:email where u.id_user =:id_user ", nativeQuery = true)
    int updateEmail(@Param("id_user")long id_user,@Param("email") String email);
    @Transactional
    @Modifying
    @Query(value = "update users u set  u.id_destination=:id_destination where u.id_user =:id_user ", nativeQuery = true)
    int updateLocation(@Param("id_user")long id_user,@Param("id_destination") Long id_destination);

}
