package edsh.blps.Repository;

import edsh.blps.entity.DopInformation;
import edsh.blps.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DopInformation_Repository  extends JpaRepository<DopInformation,Long> {
}
