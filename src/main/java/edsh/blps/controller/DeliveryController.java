package edsh.blps.controller;

import edsh.blps.dto.ApprovalDTO;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.*;
import edsh.blps.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final WorldService worldService;
    private final WarehouseService warehouseService;
    private final OrderService orderService;
    private final DopInformationService dopInformationService;
    private final UserService userService;

    public DeliveryController(WorldService worldService, WarehouseService warehouseService, OrderService orderService, DopInformationService dopInformationService, UserService userService) {
        this.worldService = worldService;
        this.warehouseService = warehouseService;
        this.orderService = orderService;
        this.dopInformationService = dopInformationService;
        this.userService = userService;
    }

    @GetMapping(value = "/raschit/{address}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Double> raschit(@PathVariable String address) {
        List<Warehouse_address> warehouse_addresses = warehouseService.get();
        World_address worldAddresses = worldService.getAddress(address);
        double min = 1000000;
        for (Warehouse_address i : warehouse_addresses) {
            System.out.println(worldAddresses.getLongitude());
            if(sqrt(pow(i.getLatitude()-worldAddresses.getLatitude(),2)+pow(i.getLongitude()-worldAddresses.getLongitude(),2))<min) {
             min=sqrt(pow(i.getLatitude()-worldAddresses.getLatitude(),2)+pow(i.getLongitude()-worldAddresses.getLongitude(),2));
            }
        }
        return new ResponseEntity<>(min * 200, HttpStatus.CREATED);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "/approval", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> approval(@RequestBody ApprovalDTO approvalDTO) {
        if(approvalDTO.getApproval()) {
            Order order = orderService.findById(approvalDTO.getId());
            order.setStatus(true);
            orderService.save(order);
        }
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
}
