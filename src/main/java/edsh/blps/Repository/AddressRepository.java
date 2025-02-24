package edsh.blps.Repository;

import edsh.blps.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,String> {
    List<Address> findByAddress(String address);
}

