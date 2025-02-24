package edsh.blps.service;

import edsh.blps.Repository.PickPointRepository;
import edsh.blps.dto.OrderDTO;
import edsh.blps.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final PickPointRepository pickPointRepository;
    private final AddressService addressService;
    private final WarehouseService warehouseService;
    private final OrderService orderService;
    private final DopInformationService dopInformationService;

    public List<PickPoint> getAllPickPoints() {
        return pickPointRepository.findAll();
    }

    public Double getMinLength(String address){
        List<Warehouse> warehouses = warehouseService.get();
        Address worldAddresses = addressService.getAddress(address);
        if(worldAddresses!=null) {
            double min = 1000000;

            for (Warehouse w : warehouses) {
                var a = w.getAddress();
                if (sqrt(pow(a.getLatitude() - worldAddresses.getLatitude(), 2) + pow(a.getLongitude() - worldAddresses.getLongitude(), 2)) < min) {
                    min = sqrt(pow(a.getLatitude() - worldAddresses.getLatitude(), 2) + pow(a.getLongitude() - worldAddresses.getLongitude(), 2));
                }
            }

            return min;
        } else {
            return null;
        }
    }

    public boolean createOrder(OrderDTO orderDTO,User user){
        try {
            DopInformation dopInformation = null;
            if (orderDTO.getDopInformationDTO() != null) {
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
            return true;
        } catch (Exception e){
            return false;
        }
    }

}



