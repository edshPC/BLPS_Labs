package edsh.blps.repository.primary;

import edsh.blps.entity.primary.DopInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DopInformationRepository extends JpaRepository<DopInformation,Long> {
}
