package edsh.blps.entity.secondary;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "blps_Payment")
@EntityListeners(PaymentListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Double amount;
    Boolean paid;
}
