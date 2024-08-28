package staj.sigorta_uygulama_staj.Exception.UserException;

public class UserAlreadyExistsException extends RuntimeException{


    public UserAlreadyExistsException(String message){
        super(message);
    }
}