package edsh.blps.service;

import edsh.blps.entity.primary.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final AddressService addressService;
    private final WarehouseService warehouseService;

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

}
