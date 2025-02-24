package edsh.blps.service;

import edsh.blps.Repository.WarehouseRepository;
import edsh.blps.entity.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> get() {
        return warehouseRepository.findAll();
    }
}

