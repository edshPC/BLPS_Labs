package edsh.blps.Repository;

import edsh.blps.entity.Warehouse_address;
import edsh.blps.entity.World_address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Warehouse_address_Repository extends JpaRepository<Warehouse_address,Long> {
}
