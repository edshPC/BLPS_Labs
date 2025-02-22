package edsh.blps.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "blps_User")
@NoArgsConstructor
public class User {
    @Id
    private String username;
}

