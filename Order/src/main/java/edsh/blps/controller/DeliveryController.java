package edsh.blps.controller;

import edsh.blps.dto.NewPaymentDTO;
import edsh.blps.dto.PaymentDTO;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.primary.User;
import edsh.blps.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final MessageSender messageSender;
    private final OrderService orderService;
    private final YookassaService yookassaService;

    @GetMapping("/get-all-pickpoints")
    public ResponseEntity<?> getAllPickPoints() throws IOException {
        return ResponseEntity.ok(messageSender.getAllPickPoint());
    }

    @GetMapping("/calculation/{address}")
    private ResponseEntity<?> calculation(@PathVariable String address) {
        Double price = messageSender.sendCalculator(address);
        return ResponseEntity.ok(price);
    }

    @PostMapping("/create")
    private ResponseEntity<?> create(@RequestBody OrderDTO orderDTO,
                                     @AuthenticationPrincipal User user) throws InterruptedException {
        NewPaymentDTO orderId = orderService.createOrder(orderDTO, user);
        yookassaService.startPaymentPolling(orderId);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }


    @PostMapping("/pay-for-order")
    private ResponseEntity<?> payForOrder(@RequestBody PaymentDTO paymentDTO) {
        orderService.payForOrder(paymentDTO);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
