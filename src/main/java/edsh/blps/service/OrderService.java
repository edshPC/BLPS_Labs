package edsh.blps.service;

import edsh.blps.Repository.OrderRepository;
import edsh.blps.entity.Order;
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
        return orderRepository.getById(id);
    }
}
