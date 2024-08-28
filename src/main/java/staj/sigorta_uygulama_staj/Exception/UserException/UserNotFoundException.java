package staj.sigorta_uygulama_staj.Exception.UserException;

public class UserNotFoundException extends RuntimeException{


    public UserNotFoundException(String message){
        super(message);
    }
}
