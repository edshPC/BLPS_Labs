package edsh.blps.entity.primary;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "blps_Order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private DeliveryMethod deliveryMethod;
    @ManyToOne
    private Address address;
    @ManyToOne
    private PickPoint pickPoint;
    private String username;
    @ManyToOne
    private DopInformation dopInformation;
    @NotNull
    private Boolean status;
}
