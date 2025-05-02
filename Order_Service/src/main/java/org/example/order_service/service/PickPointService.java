package org.example.order_service.service;

import lombok.RequiredArgsConstructor;
import org.example.order_service.entity.primary.Address;
import org.example.order_service.entity.primary.PickPoint;
import org.example.order_service.repository.primary.PickPointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickPointService {
    private final PickPointRepository pickPointRepository;

    public List<PickPoint> getAllPickPoints() {
        return pickPointRepository.findAll();
    }

    public PickPoint getByAddress(Address address) {
        return pickPointRepository.findByAddress(address)
                .orElseThrow(() -> new IllegalArgumentException("Pick point not found"));
    }
}

