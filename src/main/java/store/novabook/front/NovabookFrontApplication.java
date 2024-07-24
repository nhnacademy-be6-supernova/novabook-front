package store.novabook.front;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NovabookFrontApplication {
	public static final String APPLICATION_LOCATIONS =
		"spring.config.location=" + "classpath:application.yml," + "classpath:deployment-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(NovabookFrontApplication.class).properties(APPLICATION_LOCATIONS).run(args);
	}
}
