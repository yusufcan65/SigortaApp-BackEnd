package staj.sigorta_uygulama_staj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@SpringBootApplication
public class SigortaUygulamaStajApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigortaUygulamaStajApplication.class, args);
    }

}
