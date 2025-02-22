package edsh.blps.entity;

import jakarta.persistence.*;
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
    private UserDTO userDTO;
    private DopInformationDTO dopInformationDTO;
}
