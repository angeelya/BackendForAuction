package dip.server.repository;

import dip.server.model.ReservePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservePriceRepository extends JpaRepository<ReservePrice,Long> {
}
