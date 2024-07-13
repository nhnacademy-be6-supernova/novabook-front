package store.novabook.front.common.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import store.novabook.front.common.exception.FeignClientException;

@Order(1)
@RestControllerAdvice(annotations = HandleWithAlert.class)
public class RestExceptionHandler {

	@ExceptionHandler(FeignClientException.class)
	public ResponseEntity<String> handleSpecificFeignClientException(FeignClientException e, HttpServletRequest request,
		Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}


}

