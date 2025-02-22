package edsh.blps.controller;

import edsh.blps.entity.Warehouse_address;
import edsh.blps.entity.World_address;
import edsh.blps.service.DeliveryService;
import edsh.blps.service.WarehouseService;
import edsh.blps.service.WorldService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public DeliveryController(WorldService worldService, WarehouseService warehouseService) {
        this.worldService = worldService;
        this.warehouseService = warehouseService;
    }


    @GetMapping(value = "/raschit/{address}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Double> add(@PathVariable String address) {
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
}
