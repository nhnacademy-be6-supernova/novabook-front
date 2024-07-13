package store.novabook.front.common.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import store.novabook.front.common.exception.FeignClientException;

@Order(1)
@RestControllerAdvice(annotations = HandleWithAlert.class)
public class RestExceptionHandler {

	@ExceptionHandler(FeignClientException.class)
	public ResponseEntity<String> handleSpecificFeignClientException(FeignClientException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}

