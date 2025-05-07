package edsh.blps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PickPointDTO implements Serializable {
    private Long id;
    private AddressDTO address;
}
