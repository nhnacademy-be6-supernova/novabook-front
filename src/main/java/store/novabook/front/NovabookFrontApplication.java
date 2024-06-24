package store.novabook.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NovabookFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovabookFrontApplication.class, args);
    }

}
