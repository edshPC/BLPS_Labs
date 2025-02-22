package edsh.blps.service;

import edsh.blps.Repository.Order_Repository;
import edsh.blps.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final Order_Repository orderRepository;

    public OrderService(Order_Repository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public void save(Order order) {
        orderRepository.save(order);
    }
}
