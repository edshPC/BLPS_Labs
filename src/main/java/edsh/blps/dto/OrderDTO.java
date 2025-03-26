package edsh.blps.dto;

import edsh.blps.entity.primary.DeliveryMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private DeliveryMethod deliveryMethod;
    private String address;
    private DopInformationDTO dopInformationDTO;
}
