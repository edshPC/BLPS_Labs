package edsh.blps.Repository;

import edsh.blps.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order getById(Long id);
}
