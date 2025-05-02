package org.example.order_service.controller;


import lombok.RequiredArgsConstructor;
import org.example.order_service.dto.OrderDTO;
import org.example.order_service.dto.PaymentDTO;
import org.example.order_service.entity.primary.User;
import org.example.order_service.service.DeliveryService;
import org.example.order_service.service.PickPointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;
    private final PickPointService pickPointService;

    @GetMapping("/get-all-pickpoints")
    public ResponseEntity<?> getAllPickPoints() {
        return ResponseEntity.ok(pickPointService.getAllPickPoints());
    }

    @GetMapping("/calculation/{address}")
    private ResponseEntity<?> calculation(@PathVariable String address) {
        Double price = deliveryService.calculatePrice(address);
        return ResponseEntity.ok(price);
    }

    @PostMapping("/create")
    private ResponseEntity<?> create(@RequestBody OrderDTO orderDTO,
                                     @AuthenticationPrincipal User user) throws InterruptedException {

        Long orderId = deliveryService.createOrder(orderDTO, user);
        return new ResponseEntity<>(orderId.toString(), HttpStatus.CREATED);
    }

    @PostMapping("/pay-for-order")
    private ResponseEntity<?> payForOrder(@RequestBody PaymentDTO paymentDTO) {
        deliveryService.payForOrder(paymentDTO);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
