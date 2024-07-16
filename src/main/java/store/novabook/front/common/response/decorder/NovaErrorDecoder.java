package store.novabook.front.common.response.decorder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.common.exception.BadGatewayException;
import store.novabook.front.common.exception.BadRequestException;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.FeignClientException;
import store.novabook.front.common.exception.ForbiddenException;
import store.novabook.front.common.exception.InternalServerException;
import store.novabook.front.common.exception.NotFoundException;
import store.novabook.front.common.exception.SeeOtherException;
import store.novabook.front.common.exception.UnauthorizedException;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.ErrorResponse;

@Slf4j
public class NovaErrorDecoder implements ErrorDecoder {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Exception decode(String methodKey, Response response) {
		ErrorCode errorCode = getErrorCode(response);

		String responseBody = getResponseBody(response);

		if (responseBody != null) {
			log.info("Response Body: {}", responseBody);
		}

		return switch (HttpStatus.valueOf(response.status())) {
			case UNAUTHORIZED -> new UnauthorizedException(errorCode);
			case SEE_OTHER -> new SeeOtherException(errorCode);
			case FORBIDDEN -> new ForbiddenException(errorCode);
			case BAD_REQUEST -> new BadRequestException(errorCode);
			case NOT_FOUND -> new NotFoundException(errorCode);
			case INTERNAL_SERVER_ERROR -> new InternalServerException(errorCode);
			case BAD_GATEWAY -> new BadGatewayException(errorCode);
			default -> new FeignClientException(response.status(), errorCode);
		};
	}

	private ErrorCode getErrorCode(Response response) {
		try (InputStream bodyIs = response.body().asInputStream()) {
			ApiResponse<ErrorResponse> apiResponse = objectMapper.readValue(bodyIs,
				objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, ErrorResponse.class));
			return apiResponse.getBody().errorCode();
		} catch (Exception e) {
			return ErrorCode.DECODING_ERROR; // 에러 해석 실패 시 기본 에러 코드
		}
	}

	private String getResponseBody(Response response) {
		try (InputStream bodyIs = response.body().asInputStream()) {
			return new String(bodyIs.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			log.error("Error reading response body", e);
			return null;
		}
	}

}
