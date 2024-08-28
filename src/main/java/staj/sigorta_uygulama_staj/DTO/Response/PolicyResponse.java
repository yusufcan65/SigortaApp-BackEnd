package staj.sigorta_uygulama_staj.DTO.Response;

import lombok.Data;
import staj.sigorta_uygulama_staj.Entities.Policy;

import java.time.LocalDate;

@Data
public class PolicyResponse {

    private long id;
    private Integer policyNumber;
    private String branch_code;
    private Integer customerNumber;
    private String status;
    private Double prim;
    private Integer remainderTime;
    private LocalDate tanzim_date; //teklifin poliçeleştiği tarih
    private LocalDate start_date; //teklifin başladığı tarih
    private LocalDate finish_date;//teklifin bittiği tarih

    public PolicyResponse(Policy policy){
        this.id = policy.getId();
        this.policyNumber = policy.getPolicyNumber();
        this.branch_code = policy.getBranch_code();
        this.status=policy.getStatus();
        this.prim = policy.getPrim();
        this.customerNumber =policy.getCustomerNumber();
        this.start_date = policy.getStart_date();
        this.finish_date = policy.getFinish_date();
        this.tanzim_date = policy.getTanzim_date();
        this.remainderTime = policy.getRemainderTime();

    }

}
