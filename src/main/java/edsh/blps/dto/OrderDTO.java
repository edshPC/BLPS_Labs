package edsh.blps.dto;

import edsh.blps.entity.Way;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Way way;
    private String address;
    private DopInformationDTO dopInformationDTO;
}
