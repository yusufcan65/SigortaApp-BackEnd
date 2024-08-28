package staj.sigorta_uygulama_staj.DTO.Request;

import lombok.Data;
import staj.sigorta_uygulama_staj.Entities.Cars;

@Data
public class CreateKaskoPolicyRequest{

    private Cars car;
    private long CustomerId;
    private long userId;


    private String plateCityCode;
    private String plateCode;
    private String engineNumber;
    private String frameNumber;


}
