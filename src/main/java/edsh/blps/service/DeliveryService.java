package edsh.blps.service;

import edsh.blps.dto.PaymentDTO;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.primary.*;
import edsh.blps.entity.secondary.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final AddressService addressService;
    private final WarehouseService warehouseService;
    private final OrderService orderService;
    private final DopInformationService dopInformationService;
    private final PickPointService pickPointService;
    private final JTAConfirmService jtaConfirmService;
    private final PlatformTransactionManager transactionManager;

    public Double getMinLength(String address) {
        List<Warehouse> warehouses = warehouseService.get();
        Address worldAddresses = addressService.getAddress(address);
        if (worldAddresses == null)
            throw new IllegalArgumentException("Address not found");
        double min = 1000000;
        for (Warehouse w : warehouses) {
            var a = w.getAddress();
            if (sqrt(pow(a.getLatitude() - worldAddresses.getLatitude(), 2) + pow(a.getLongitude() - worldAddresses.getLongitude(), 2)) < min) {
                min = sqrt(pow(a.getLatitude() - worldAddresses.getLatitude(), 2) + pow(a.getLongitude() - worldAddresses.getLongitude(), 2));
            }
        }
        return min;
    }

    public Double calculatePrice(String address) {
        return getMinLength(address) * 2000;
    }

    public void createOrder(OrderDTO orderDTO, User user) {
        DopInformation dopInformation = null;
        if (orderDTO.getDopInformationDTO() != null) {
            dopInformation = DopInformation.builder()
                    .floor(orderDTO.getDopInformationDTO().getFloor())
                    .flat(orderDTO.getDopInformationDTO().getFlat())
                    .intercom_system(orderDTO.getDopInformationDTO().getIntercom_system())
                    .entrance(orderDTO.getDopInformationDTO().getEntrance())
                    .comment_to_the_courier(orderDTO.getDopInformationDTO().getComment_to_the_courier())
                    .price(calculatePrice(orderDTO.getAddress()))
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

        CompletableFuture.runAsync(() -> jtaConfirmService.createOrder(order));
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
        orderService.save(order);
    }

}
