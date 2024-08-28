package staj.sigorta_uygulama_staj.Service.Impl;

import org.springframework.stereotype.Service;
import staj.sigorta_uygulama_staj.DTO.Request.CreateCustomerRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UpdateCustomerRequest;
import staj.sigorta_uygulama_staj.DTO.Response.CustomerResponse;
import staj.sigorta_uygulama_staj.DTO.Response.CustomerResponseUpdate;
import staj.sigorta_uygulama_staj.Entities.Customer;
import staj.sigorta_uygulama_staj.Entities.Users;
import staj.sigorta_uygulama_staj.Exception.CustomerException.CustomerNotFoundException;
import staj.sigorta_uygulama_staj.Repository.CustomerRepository;
import staj.sigorta_uygulama_staj.Service.CustomerService;
import staj.sigorta_uygulama_staj.Service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final UserService userService;
    private final CustomerRepository customerRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository,UserService userService){
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    @Override
    public void CreateCustomer(CreateCustomerRequest createCustomerRequest) {

        Long userId = createCustomerRequest.getUserId();
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID is required and must be a positive number");
        }

        Users user = this.userService.getByUserId(userId);


        //Users user = this.userService.getByUserId(createCustomerRequest.getUserId());

        if(customerRepository.findByIdNumber(createCustomerRequest.getId_number()).isPresent()){
            throw new RuntimeException("customer already exists");
        }
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setName(createCustomerRequest.getName());
            customer.setSurname(createCustomerRequest.getSurname());
            customer.setId_number(createCustomerRequest.getId_number());
            customer.setBirth_date(createCustomerRequest.getBirth_date());
            customer.setCity(createCustomerRequest.getCity());
            customer.setDistrict(createCustomerRequest.getDistrict());
            customer.setPhone_number(createCustomerRequest.getPhone_number());
            customer.setEmail(createCustomerRequest.getEmail());
            customer.setCustomerNumber(generateUniqueCustomerNumber());

            customerRepository.save(customer);

    }

    @Override
    public List<CustomerResponse> getAll() {

        List<Customer> customerlist=customerRepository.findAll();
        return customerlist.stream().map(CustomerResponse::new).collect(Collectors.toList());

    }



    @Override
    public Customer updateCustomer(UpdateCustomerRequest updateCustomerRequest) {


        Customer customer = getById(updateCustomerRequest.getId());

        customer.setName(updateCustomerRequest.getName());
        customer.setSurname(updateCustomerRequest.getSurname());
        customer.setId_number(updateCustomerRequest.getId_number());
        customer.setBirth_date(updateCustomerRequest.getBirth_date());
        customer.setCity(updateCustomerRequest.getCity());
        customer.setDistrict(updateCustomerRequest.getDistrict());
        customer.setPhone_number(updateCustomerRequest.getPhone_number());
        customer.setEmail(updateCustomerRequest.getEmail());

        return customerRepository.save(customer);

    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponseUpdate getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id).orElseThrow();

        CustomerResponseUpdate dto = new CustomerResponseUpdate(customer);
        dto.setName(customer.getName());
        dto.setSurname(customer.getSurname());
        dto.setBirth_date(customer.getBirth_date());
        dto.setCity(customer.getCity());
        dto.setDistrict(customer.getDistrict());
        dto.setPhone_number(customer.getPhone_number());
        dto.setEmail(customer.getEmail());
        dto.setId_number(customer.getId_number());

        return dto;

    }
    @Override
    public List<CustomerResponse> getByUserId(long user_id){
        List<Customer> customerList = customerRepository.findByUserId(user_id);
        return customerList.stream().map(CustomerResponse::new).collect(Collectors.toList());

    }
    @Override
    public Customer getByName(String name){
        return customerRepository.findByName(name);
    }

    // tc kimlik numarası ile filtreleme
    @Override
    public Customer getCustomerByIdNumber(String id_number) {

        Customer customer = customerRepository.findByIdNumber(id_number).orElseThrow(()->new CustomerNotFoundException( " "+id_number+" tc numaralı customer mevcut değil"));
        return customer;
    }
    //müşteri numarası ile filtereleme
    @Override
    public Customer getByCustomerNumber(Integer customerNumber) {

        Customer customer = customerRepository.findByCustomerNumber(customerNumber);
        return customer;
    }

    public Integer generateUniqueCustomerNumber() {
        Random random = new Random();
        int customer_number;
        boolean exists;

        do {
            customer_number = 10000000 + random.nextInt(90000000); // 8 haneli rastgele sayı oluştur
            exists = customerRepository.existsByCustomerNumber(customer_number); // Mevcut olup olmadığını kontrol et
        } while (exists);

        return customer_number;
    }

    public Customer getById(long id){
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(id));
        if (customer.isPresent()) {
            return customer.get();
        } else {

            // Loglama veya hata işleme burada yapılabilir
            return null;
        }
    }
}
