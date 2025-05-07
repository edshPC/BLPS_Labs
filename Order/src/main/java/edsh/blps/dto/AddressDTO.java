package edsh.blps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO implements Serializable{
    private Long id;
    String address;
    double longitude;
    double latitude;
}

