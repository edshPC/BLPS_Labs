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
    private final AddressService addressService;
    private final WarehouseService warehouseService;
    private final OrderService orderService;
    private final DopInformationService dopInformationService;
    private final DeliveryService deliveryService;

    @GetMapping("/get-all-pickpoints")
    public ResponseEntity<?> getAllPickPoints() {
        return ResponseEntity.ok(deliveryService.getAllPickPoints());
    }

    @GetMapping("/raschit/{address}")
    private ResponseEntity<Double> raschit(@PathVariable String address) {
        List<Warehouse> warehouses = warehouseService.get();
        Address worldAddresses = addressService.getAddress(address);
        double min = 1000000;
        for (Warehouse w : warehouses) {
            System.out.println(worldAddresses.getLongitude());
            var a = w.getAddress();
            if(sqrt(pow(a.getLatitude()-worldAddresses.getLatitude(),2)+pow(a.getLongitude()-worldAddresses.getLongitude(),2))<min) {
             min=sqrt(pow(a.getLatitude()-worldAddresses.getLatitude(),2)+pow(a.getLongitude()-worldAddresses.getLongitude(),2));
            }
        }
        return new ResponseEntity<>(min * 200, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    private ResponseEntity<String> create(@RequestBody OrderDTO orderDTO,
                                          @AuthenticationPrincipal User user) {
        DopInformation dopInformation = null;
        if(orderDTO.getDopInformationDTO()!=null) {
            dopInformation = DopInformation.builder()
                    .floor(orderDTO.getDopInformationDTO().getFloor())
                    .flat(orderDTO.getDopInformationDTO().getFlat())
                    .intercom_system(orderDTO.getDopInformationDTO().getIntercom_system())
                    .entrance(orderDTO.getDopInformationDTO().getEntrance())
                    .comment_to_the_courier(orderDTO.getDopInformationDTO().getComment_to_the_courier())
                    .build();
            dopInformationService.save(dopInformation);
        }
        Order order = Order.builder().way(orderDTO.getWay()).user(user).address(orderDTO.getAddress())
                .dopInformation(dopInformation).status(false).build();
        orderService.save(order);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
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
