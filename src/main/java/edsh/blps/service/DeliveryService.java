package edsh.blps.service;

import edsh.blps.Repository.PickPointRepository;
import edsh.blps.entity.PickPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final PickPointRepository pickPointRepository;

    public List<PickPoint> getAllPickPoints() {
        return pickPointRepository.findAll();
    }

}



