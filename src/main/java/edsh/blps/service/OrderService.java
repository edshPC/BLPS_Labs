package edsh.blps.service;

import edsh.blps.dto.OrderDTO;
import edsh.blps.dto.PaymentDTO;
import edsh.blps.entity.primary.DeliveryMethod;
import edsh.blps.entity.primary.DopInformation;
import edsh.blps.entity.primary.User;
import edsh.blps.entity.secondary.Payment;
import edsh.blps.repository.primary.OrderRepository;
import edsh.blps.entity.primary.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AddressService addressService;
    private final PickPointService pickPointService;
    private final JTAConfirmService jtaConfirmService;
    private final DeliveryService deliveryService;

    public void save(Order order) {
        orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Order not found"));
    }

    public Long createOrder(OrderDTO orderDTO, User user) throws InterruptedException {
        DopInformation dopInformation = null;
        if (orderDTO.getDopInformationDTO() != null) {
            dopInformation = DopInformation.builder()
                    .floor(orderDTO.getDopInformationDTO().getFloor())
                    .flat(orderDTO.getDopInformationDTO().getFlat())
                    .intercom_system(orderDTO.getDopInformationDTO().getIntercom_system())
                    .entrance(orderDTO.getDopInformationDTO().getEntrance())
                    .comment_to_the_courier(orderDTO.getDopInformationDTO().getComment_to_the_courier())
                    .price(deliveryService.calculatePrice(orderDTO.getAddress()))
                    .build();
        }
        Order order = Order.builder()
                .deliveryMethod(orderDTO.getDeliveryMethod())
                .username(user.getUsername())
                .address(addressService.getAddress(orderDTO.getAddress()))
                .dopInformation(dopInformation)
                .status(false).build();
        if (order.getDeliveryMethod() == DeliveryMethod.pickup) {
            order.setPickPoint(pickPointService.getByAddress(order.getAddress()));
        }
        System.out.println(order);
        BlockingQueue<Long> dataQueue = new LinkedBlockingQueue<>();

        CompletableFuture.runAsync(() -> {
            jtaConfirmService.createOrder(order,dataQueue);
        });
        Long data = dataQueue.poll(20, TimeUnit.SECONDS); // Ожидание 5 секунд
        if (data != null) {
            return data;
            //System.out.println("Полученные данные: " + data);
        } else {
            throw new IllegalArgumentException("Ошибка");
            //System.out.println("Данные не были получены за отведенное время.");
        }
    }

    public void payForOrder(PaymentDTO paymentDTO) {
        Payment payment = Payment.builder()
                .amount(paymentDTO.getAmount())
                .paid(paymentDTO.getSuccess())
                .orderId(paymentDTO.getOrderId())
                .build();
        jtaConfirmService.onPaymentUpdate(payment);
    }

    public void approveOrder(Order order) {
        order.setStatus(true);
        save(order);
    }

}
