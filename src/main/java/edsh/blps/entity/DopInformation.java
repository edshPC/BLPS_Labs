package edsh.blps.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "blps_DopInformational")
@NoArgsConstructor
public class DopInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String address;
    String flat;
    String entrance;
    String floor;
    String intercom_system;
    String comment_to_the_courier;
    String name_and_surname;
    int telephone;
}
