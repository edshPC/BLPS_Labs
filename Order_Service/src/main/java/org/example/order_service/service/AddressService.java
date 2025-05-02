package org.example.order_service.service;

import lombok.RequiredArgsConstructor;
import org.example.order_service.entity.primary.Address;
import org.example.order_service.repository.primary.AddressRepository;
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

