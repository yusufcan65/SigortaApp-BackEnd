package staj.sigorta_uygulama_staj.Entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer policyNumber;
    private String addressCode;
    private Double squareMeter;//metre kare
    private int floorNumber;//bulunduğğu kat
    private String BuildStyle;  //bina yapı tarzı
    private int numberBuildFloor;//kat sayısı
    private String damageState;// hasar durumu
    private int buildingAge;//inşa yılı


}
