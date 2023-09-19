package dip.server.repository;

import dip.server.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination,Long> {
    @Query(value = "select * from destination where id_destination=:id_destination",nativeQuery = true)
    Destination findDestination(@Param("id_destination") long id_destination);
    @Query(value = "select * from destination",nativeQuery = true)
    List<Destination> getDestination();
}
