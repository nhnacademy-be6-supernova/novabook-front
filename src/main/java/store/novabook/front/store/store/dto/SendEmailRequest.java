package store.novabook.front.store.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendEmailRequest(
	@NotBlank
	String name,
	@Email
	String email,
	String orderNumber,
	@NotBlank
	String subject,
	@NotBlank
	String message
) {
}