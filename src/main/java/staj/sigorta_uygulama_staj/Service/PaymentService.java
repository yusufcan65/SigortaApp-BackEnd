package staj.sigorta_uygulama_staj.Service;

import staj.sigorta_uygulama_staj.DTO.Request.CreatePaymentRequest;
import staj.sigorta_uygulama_staj.Entities.Payment;
import staj.sigorta_uygulama_staj.Entities.Policy;

public interface PaymentService {

    void doPayment(CreatePaymentRequest createPaymentRequest,long policy_id);


}
