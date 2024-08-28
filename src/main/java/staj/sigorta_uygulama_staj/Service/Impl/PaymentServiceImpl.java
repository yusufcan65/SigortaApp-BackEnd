package staj.sigorta_uygulama_staj.Service.Impl;

import org.springframework.stereotype.Service;
import staj.sigorta_uygulama_staj.DTO.Request.CreatePaymentRequest;
import staj.sigorta_uygulama_staj.Entities.Payment;
import staj.sigorta_uygulama_staj.Entities.Policy;
import staj.sigorta_uygulama_staj.Repository.PaymentRepository;
import staj.sigorta_uygulama_staj.Repository.PolicyRepository;
import staj.sigorta_uygulama_staj.Service.PaymentService;

import java.time.LocalDate;
import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final PolicyRepository policyRepository;


    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PolicyRepository policyRepository){
        this.paymentRepository = paymentRepository;
        this.policyRepository=policyRepository;
    }
    @Override
    public void doPayment(CreatePaymentRequest createPaymentRequest,long policy_id) {

        Policy policy = this.policyRepository.findById(policy_id).orElseThrow();
        if(policy != null){
            Payment payment = new Payment();
            payment.setPolicy_number(policy.getPolicyNumber());// ödeme tablosunda yer alan police numarası kısmına ödemesi yapılacak poliçenin poliçe numarası eklenir
            payment.setCvv(createPaymentRequest.getCvv());
            payment.setCard_number(createPaymentRequest.getCard_number());
            payment.setExpiry_date(createPaymentRequest.getExpiry_date());
            payment.setAmount(policy.getPrim());
            payment.setPayment_date(new Date());
            payment.setCard_owner(createPaymentRequest.getCard_owner());
            policy.setStatus("P");
            policy.setRemainderTime(0);
            policy.setStart_date(LocalDate.now());
            policy.setFinish_date(policy.getStart_date().plusYears(1)); // bunu sorabilirsin olması gereken bu şekilde
            policyRepository.save(policy);
            paymentRepository.save(payment);

        }
        else{
            throw new RuntimeException("ödeme  başarısız");

        }


    }
}
