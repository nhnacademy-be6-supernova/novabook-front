package store.novabook.front.api.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SwaggerController {

	private final RestTemplate restTemplate;

	@GetMapping("/swagger/store/json")
	public ResponseEntity<String> getStoreSwaggerHTML() {
		String url = "http://localhost:8090/api-docs";
		String response = restTemplate.getForObject(url, String.class);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/swagger/coupon/json")
	public ResponseEntity<String> getCouponSwaggerHTML() {
		String url = "http://localhost:8070/api-docs";
		String response = restTemplate.getForObject(url, String.class);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/swagger/{serviceName}")
	public String getSwaggerHTML(@PathVariable String serviceName, Model model) {
		if (serviceName.equals("store")) {
			model.addAttribute("path", "/swagger/store/json");
		} else if (serviceName.equals("coupon")) {
			model.addAttribute("path", "/swagger/coupon/json");
		}
		return "common/swagger";
	}
}
