package store.novabook.front.common.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import store.novabook.front.common.exception.NovaException;
import store.novabook.front.common.response.ValidErrorResponse;

@Order(1)
@RestControllerAdvice(annotations = HandleWithAlert.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * {@inheritDoc}
	 * <p>
	 * 유효하지 않은 메서드 인자 예외를 처리하여 {@link ValidErrorResponse}를 반환합니다.
	 * </p>
	 *
	 * @param exception 유효하지 않은 메서드 인자 예외
	 * @param headers   응답 헤더
	 * @param status    HTTP 상태 코드
	 * @param request   웹 요청
	 * @return {@link ValidErrorResponse}를 포함하는 {@link ResponseEntity} 객체
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidErrorResponse.from(exception).message());
	}

	@ExceptionHandler(NovaException.class)
	public ResponseEntity<String> handleSpecificFeignClientException(NovaException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}

