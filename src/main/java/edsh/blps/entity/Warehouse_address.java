package edsh.blps.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Data
@Entity
@Builder
@Table(name = "blps_Warehouse_address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Warehouse_address {

    @Id
    String address;
    double longitude;
    double latitude;
}
