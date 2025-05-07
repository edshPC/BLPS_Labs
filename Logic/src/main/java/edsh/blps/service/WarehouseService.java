package edsh.blps.service;

import edsh.blps.repository.primary.WarehouseRepository;
import edsh.blps.entity.primary.Warehouse;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> get() {
        return warehouseRepository.findAll();
    }
}

