package org.example.order_service.repository.primary;

import org.example.order_service.entity.primary.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,String> {
    List<Address> findByAddress(String address);
}

