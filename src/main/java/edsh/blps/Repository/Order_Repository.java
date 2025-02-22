package edsh.blps.Repository;

import edsh.blps.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Order_Repository extends JpaRepository<Order,Long> {
    Order getById(Long id);
}
