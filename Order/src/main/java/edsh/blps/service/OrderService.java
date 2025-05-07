package edsh.blps.service;

import edsh.blps.dto.NewPaymentDTO;
import edsh.blps.dto.OrderDTO;
import edsh.blps.dto.PaymentDTO;
import edsh.blps.entity.primary.DeliveryMethod;
import edsh.blps.entity.primary.DopInformation;
import edsh.blps.entity.primary.User;
import edsh.blps.entity.secondary.Payment;
import edsh.blps.repository.primary.OrderRepository;
import edsh.blps.entity.primary.Order;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.UUID;
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
    private final YookassaService yookassaService;

    public void save(Order order) {
        orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Order not found"));
    }

    @SneakyThrows
    public NewPaymentDTO createOrder(OrderDTO orderDTO, User user) {
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

        CompletableFuture<Void> orderIdAwait = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> jtaConfirmService.createOrder(order, orderIdAwait));
        orderIdAwait.get(20, TimeUnit.SECONDS);

        return yookassaService.createNewPaymentFor(order);
    }

    public void payForOrder(PaymentDTO paymentDTO) {
        Payment payment = Payment.builder()
                .amount(paymentDTO.getAmount())
                .paid(paymentDTO.getSuccess())
                .orderId(paymentDTO.getOrderId())
                .paymentId(paymentDTO.getPaymentId() != null ? paymentDTO.getPaymentId().toString() : null)
                .build();
        jtaConfirmService.onPaymentUpdate(payment);
        System.out.println("Payment #" + paymentDTO.getOrderId() + " success: " + paymentDTO.getSuccess());
    }

    public void approveOrder(Order order) {
        order.setStatus(true);
        save(order);
    }

}