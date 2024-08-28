package staj.sigorta_uygulama_staj.DTO.Request;


import lombok.Data;

@Data
public class CreateDaskPolicyRequest{

    private long userId;
    private long customerId;
    private String addressCode;
    private Double squareMeter;//metre kare
    private int floorNumber;//bulunduğğu kat
    private String BuildStyle;  //bina yapı tarzı
    private int numberBuildFloor;//kat sayısı
    private String damageState;// hasar durumu
    private int buildingAge;//inşa yılı

}
