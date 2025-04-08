package edsh.blps.service;

import edsh.blps.repository.primary.DopInformationRepository;
import edsh.blps.entity.primary.DopInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
@RequiredArgsConstructor
public class DopInformationService {
    private final DopInformationRepository dopInformationRepository;
    private final PlatformTransactionManager transactionManager;

    public void save(DopInformation dopInformation) {
        dopInformationRepository.save(dopInformation);
    }
}
