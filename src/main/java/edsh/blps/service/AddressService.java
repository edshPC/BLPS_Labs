package edsh.blps.service;

import edsh.blps.Repository.AddressRepository;
import edsh.blps.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address getAddress(String address) {
        System.out.println(address);
        return addressRepository.findByAddress(address).get(0);
    }
}

