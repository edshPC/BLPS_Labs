package edsh.blps.entity.primary;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@Table(name = "blps_Address")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String address;
    double longitude;
    double latitude;
}
