package edsh.blps.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "blps_DopInformation")
@AllArgsConstructor
@NoArgsConstructor
public class DopInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String flat;
    String entrance;
    String floor;
    String intercom_system;
    String comment_to_the_courier;
    Double price;
}
