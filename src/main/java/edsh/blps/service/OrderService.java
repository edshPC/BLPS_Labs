package edsh.blps.service;

import edsh.blps.repository.primary.OrderRepository;
import edsh.blps.entity.primary.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public void save(Order order) {
        orderRepository.save(order);
    }
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Order not found"));
    }
}
