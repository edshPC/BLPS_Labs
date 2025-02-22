package edsh.blps.Repository;

import edsh.blps.entity.World_address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface World_address_Repository extends JpaRepository<World_address,String> {
    List<World_address> findByAddress(String address);
}

