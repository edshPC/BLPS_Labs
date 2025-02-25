package edsh.blps.Repository;

import edsh.blps.entity.Address;
import edsh.blps.entity.PickPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PickPointRepository extends JpaRepository<PickPoint, Long> {
    Optional<PickPoint> findByAddress(Address address);
}
