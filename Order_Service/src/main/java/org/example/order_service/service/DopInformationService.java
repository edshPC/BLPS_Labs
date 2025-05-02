package org.example.order_service.service;

import lombok.RequiredArgsConstructor;
import org.example.order_service.entity.primary.DopInformation;
import org.example.order_service.repository.primary.DopInformationRepository;
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
