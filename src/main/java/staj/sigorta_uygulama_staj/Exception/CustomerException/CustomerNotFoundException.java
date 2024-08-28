package staj.sigorta_uygulama_staj.Exception.CustomerException;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException (String message){
        super(message);
    }
}
