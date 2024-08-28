package staj.sigorta_uygulama_staj.DTO.Response;

import lombok.Data;
import staj.sigorta_uygulama_staj.Entities.Cars;

@Data
public class CarResponse {

    private String brand;
    private String model;
    private Integer model_year;

    public CarResponse(Cars car){
        this.brand= car.getBrand();
        this.model= car.getModel();
        this.model_year= car.getModelYear();
    }

}
