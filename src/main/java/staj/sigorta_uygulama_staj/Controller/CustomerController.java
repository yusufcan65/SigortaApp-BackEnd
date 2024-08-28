package staj.sigorta_uygulama_staj.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import staj.sigorta_uygulama_staj.DTO.Request.CreateCustomerRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UpdateCustomerRequest;
import staj.sigorta_uygulama_staj.DTO.Response.CustomerResponse;
import staj.sigorta_uygulama_staj.DTO.Response.CustomerResponseUpdate;
import staj.sigorta_uygulama_staj.Entities.Customer;
import staj.sigorta_uygulama_staj.Exception.CustomerException.CustomerNotFoundException;
import staj.sigorta_uygulama_staj.Service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<Map<String, String>> addCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        this.customerService.CreateCustomer(createCustomerRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Customer added successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getByUserId/{user_id}")
    public List<CustomerResponse> getByUserId(@PathVariable long user_id){
        return this.customerService.getByUserId(user_id);
    }

    @PutMapping("/update")
    public Customer updateCustomer(@RequestBody UpdateCustomerRequest updateCustomerRequest){
        return this.customerService.updateCustomer(updateCustomerRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable long id){
        this.customerService.deleteCustomer(id);
    }

    @GetMapping
    public List<CustomerResponse> getAll(){
        return customerService.getAll();
    }
    // tc kimlik numarası ile filtreleme
    @GetMapping("/{id_number}")
    public ResponseEntity<Object> GetByIdNumberSearch(@PathVariable String id_number){
        try{
            return new ResponseEntity<>(customerService.getCustomerByIdNumber(id_number),HttpStatus.OK) ;
        }catch (CustomerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/getByIdNumber/{id_number}")
    public Customer getByCustomerIdNumber(@PathVariable String id_number){
        return this.customerService.getCustomerByIdNumber(id_number);
    }
    // update edilecek customeri bulan metot
    @GetMapping("/id/{id}")
    public ResponseEntity<CustomerResponseUpdate> getById(@PathVariable long id){
        CustomerResponseUpdate customerDTO = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerDTO);
    }

    // müşteri numarası ile filtreleme
    @GetMapping("/customerNumber/{customerNumber}")
    public Customer getByCustomerNumber(@PathVariable Integer customerNumber){
        return this.customerService.getByCustomerNumber(customerNumber);
    }
    @GetMapping("/customerName/{customerName}")
    public Customer getByCustomerName(@PathVariable String customerName){
        return this.customerService.getByName(customerName);
    }


}
