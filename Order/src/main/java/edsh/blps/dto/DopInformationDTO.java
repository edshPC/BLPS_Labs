package edsh.blps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DopInformationDTO {
    String flat;
    String entrance;
    String floor;
    String intercom_system;
    String comment_to_the_courier;
}

