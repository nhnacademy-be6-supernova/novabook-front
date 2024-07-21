package store.novabook.front.store.store;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.common.handler.HandleWithAlert;
import store.novabook.front.store.store.dto.SendEmailRequest;
import store.novabook.front.store.store.service.EmailService;

@HandleWithAlert
@RestController
@RequestMapping("/contact-us")
@RequiredArgsConstructor
public class ContactRestController {

	private final EmailService emailService;

	@PostMapping("/sendEmail")
	public void sendEmail(@Valid @RequestBody SendEmailRequest request) {
		emailService.sendEmail(request);
	}
}
