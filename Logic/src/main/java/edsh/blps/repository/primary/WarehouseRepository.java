package edsh.blps.repository.primary;

import edsh.blps.entity.primary.Warehouse;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
}

