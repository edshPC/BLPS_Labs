package org.example.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.order_service.entity.primary.DeliveryMethod;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private DeliveryMethod deliveryMethod;
    private String address;
    private DopInformationDTO dopInformationDTO;
}
