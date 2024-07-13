package store.novabook.front.api.admin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "gateway-service", path = "/api/v1/store/admin", contextId = "AdminClient")
public interface AdminClient {

	@GetMapping()
	ResponseEntity<Void> admin();
}
