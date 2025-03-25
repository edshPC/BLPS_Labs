package edsh.blps.controller;

import edsh.blps.dto.ApprovalDTO;
import edsh.blps.dto.DopInformationDTO;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.*;
import edsh.blps.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
                                     @AuthenticationPrincipal User user) {
        deliveryService.createOrder(orderDTO, user);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

    @PostMapping("/approval")
    private ResponseEntity<?> approval(@RequestBody ApprovalDTO approvalDTO) {
        deliveryService.approveOrder(approvalDTO);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
}
