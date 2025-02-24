package edsh.blps.controller;

import edsh.blps.dto.ApprovalDTO;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.*;
import edsh.blps.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final OrderService orderService;
    private final DeliveryService deliveryService;

    @GetMapping("/get-all-pickpoints")
    public ResponseEntity<?> getAllPickPoints() {
        return ResponseEntity.ok(deliveryService.getAllPickPoints());
    }

    @GetMapping("/calculation/{address}")
    private ResponseEntity<Double> calculation(@PathVariable String address) {
        Double length = deliveryService.getMinLength(address);
        if(length==null){
            return new ResponseEntity<>(-1.0,
                    HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(length * 200,
                    HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    private ResponseEntity<String> create(@RequestBody OrderDTO orderDTO,
                                          @AuthenticationPrincipal User user) {
        if(deliveryService.createOrder(orderDTO,user)) {
            return new ResponseEntity<>("OK", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Ошибка", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/approval")
    private ResponseEntity<String> approval(@RequestBody ApprovalDTO approvalDTO) {
        if(approvalDTO.getApproval()) {
            Order order = orderService.findById(approvalDTO.getId());
            order.setStatus(true);
            orderService.save(order);
        }
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
}
