package edsh.blps.dto;

import edsh.blps.entity.primary.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
