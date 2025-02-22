package edsh.blps.service;

import edsh.blps.Repository.DopInformation_Repository;
import edsh.blps.entity.DopInformation;
import org.springframework.stereotype.Service;

@Service
public class DopInformationService {
    private final DopInformation_Repository dopInformationRepository;

    public DopInformationService(DopInformation_Repository dopInformationRepository) {
        this.dopInformationRepository = dopInformationRepository;
    }

    public void save(DopInformation dopInformation) {
        dopInformationRepository.save(dopInformation);
    }
}
