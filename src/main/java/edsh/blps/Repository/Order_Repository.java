package edsh.blps.Repository;

import edsh.blps.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Order_Repository extends JpaRepository<Order,Long> {
}
