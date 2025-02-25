package edsh.blps.service;

import edsh.blps.Repository.DopInformationRepository;
import edsh.blps.entity.DopInformation;
import org.springframework.stereotype.Service;

@Service
public class DopInformationService {
    private final DopInformationRepository dopInformationRepository;

    public DopInformationService(DopInformationRepository dopInformationRepository) {
        this.dopInformationRepository = dopInformationRepository;
    }

    public void save(DopInformation dopInformation) {
        dopInformationRepository.save(dopInformation);
    }
}
