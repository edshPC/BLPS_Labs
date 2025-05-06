package edsh.blps.service;

import edsh.blps.repository.primary.PickPointRepository;
import edsh.blps.entity.primary.Address;
import edsh.blps.entity.primary.PickPoint;
import lombok.RequiredArgsConstructor;
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

