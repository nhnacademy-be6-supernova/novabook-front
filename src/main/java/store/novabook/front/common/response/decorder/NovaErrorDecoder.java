package store.novabook.front.common.response.decorder;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.FeignClientException;
import store.novabook.front.common.exception.SeeOtherException;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.ErrorResponse;

public class NovaErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Exception decode(String methodKey, Response response) {
		try (InputStream bodyIs = response.body().asInputStream()) {
			ApiResponse<ErrorResponse> apiResponse = objectMapper.readValue(bodyIs,
				objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, ErrorResponse.class));
			ErrorResponse errorResponse = apiResponse.getBody();
			return new FeignClientException(response.status(), errorResponse.errorCode());
		} catch (IOException e) {
			return defaultErrorDecoder.decode(methodKey, response);
		}
	}
}
