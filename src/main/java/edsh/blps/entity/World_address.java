package edsh.blps.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@Table(name = "blps_World_address")
@NoArgsConstructor
@AllArgsConstructor
public class World_address {
    @Getter
    @Setter
    @Id
    String address;
    double longitude;
    double latitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
