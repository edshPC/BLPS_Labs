package edsh.blps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPaymentDTO {
     Long orderId;
     Double amount;
     UUID paymentId;
     String paymentUrl;
}
