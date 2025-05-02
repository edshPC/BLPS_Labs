package org.example.order_service.repository.primary;

import org.example.order_service.entity.primary.DopInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DopInformationRepository extends JpaRepository<DopInformation,Long> {
}
