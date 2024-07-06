package store.novabook.front.api.member.member.dto;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import store.novabook.front.api.member.member.dto.response.PaycoLoginApiResponse;
import store.novabook.front.api.member.member.dto.response.PaycoLogoutResponse;

@Component
public class PaycoResponseValidator {

	private final ObjectMapper objectMapper;

	public PaycoResponseValidator(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public Optional<String> validateGetPaycoId(String jsonResponse) {
		try {
			PaycoLoginApiResponse response = objectMapper.readValue(jsonResponse, PaycoLoginApiResponse.class);
			String resultMessage = response.getHeader().getResultMessage();
			if ("SUCCESS".equalsIgnoreCase(resultMessage)) {
				return Optional.ofNullable(response.getData().getMember().getIdNo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public boolean validateLogout(String jsonResponse) {
		try {
			PaycoLogoutResponse response = objectMapper.readValue(jsonResponse, PaycoLogoutResponse.class);
			return "success".equalsIgnoreCase(response.getReturnMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
