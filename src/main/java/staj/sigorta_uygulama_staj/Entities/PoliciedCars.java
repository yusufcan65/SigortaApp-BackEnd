package staj.sigorta_uygulama_staj.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliciedCars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer policy_number;
    private String plateCityCode;
    private String plateCode;
    private String engineNumber;
    private String frameNumber; // şasi numarası
    private Long carId;


}
