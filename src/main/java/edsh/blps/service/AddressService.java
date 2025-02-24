package edsh.blps.service;

import edsh.blps.Repository.AddressRepository;
import edsh.blps.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address getAddress(String address) {
        List<Address> addresses=addressRepository.findByAddress(address);
        if(addresses.isEmpty()){
            return null;
        } else {
            return addresses.get(0);
        }
    }
}

