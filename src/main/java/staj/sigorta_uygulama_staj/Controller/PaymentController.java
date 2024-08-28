package staj.sigorta_uygulama_staj.Controller;

import org.springframework.web.bind.annotation.*;
import staj.sigorta_uygulama_staj.DTO.Request.CreatePaymentRequest;
import staj.sigorta_uygulama_staj.Service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {


    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/{policyId}")
    public void doPayment(@RequestBody CreatePaymentRequest createPaymentRequest,@PathVariable long policyId){
        paymentService.doPayment(createPaymentRequest,policyId);
    }
}
