package staj.sigorta_uygulama_staj.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer age;
    private String smokeStatus ;
    private String sporStatus;
    private String operationStatus;
    private String chronicDiseaseStatus;
    private Integer policy_number;




}
