package org.example.order_service.service;

import lombok.RequiredArgsConstructor;
import org.example.order_service.entity.primary.Warehouse;
import org.example.order_service.repository.primary.WarehouseRepository;
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

