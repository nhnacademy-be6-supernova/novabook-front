package store.novabook.front.api.swagger.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "swaggerClient", url = "http://localhost:8090")
public interface SwaggerClient {
	@GetMapping("/api-docs")
	ResponseEntity<String> getSwaggerHTML();
}
