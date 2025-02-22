package edsh.blps.Repository;

import edsh.blps.entity.Order;
import edsh.blps.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_Repository  extends JpaRepository<User,Long> {

}
