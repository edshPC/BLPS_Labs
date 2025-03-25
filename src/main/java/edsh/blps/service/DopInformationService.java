package edsh.blps.service;

import edsh.blps.Repository.DopInformationRepository;
import edsh.blps.entity.DopInformation;
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
