package edsh.blps.repository.primary;

import edsh.blps.entity.primary.Address;
import edsh.blps.entity.primary.PickPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PickPointRepository extends JpaRepository<PickPoint, Long> {
    Optional<PickPoint> findByAddress(Address address);
}
