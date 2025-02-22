package edsh.blps.service;

import edsh.blps.Repository.Warehouse_address_Repository;
import edsh.blps.entity.Warehouse_address;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private final Warehouse_address_Repository warehouse_address_repository;

    public WarehouseService(Warehouse_address_Repository warehouseAddressRepository) {
        warehouse_address_repository = warehouseAddressRepository;
    }

    public List<Warehouse_address> get() {
        return warehouse_address_repository.findAll();
    }
}

