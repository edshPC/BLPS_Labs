package org.example.order_service.repository.primary;


import org.example.order_service.entity.primary.Address;
import org.example.order_service.entity.primary.PickPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PickPointRepository extends JpaRepository<PickPoint, Long> {
    Optional<PickPoint> findByAddress(Address address);
}
