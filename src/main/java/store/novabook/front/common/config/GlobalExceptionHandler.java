package store.novabook.front.common.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

// @ControllerAdvice
public class GlobalExceptionHandler {

	// @ExceptionHandler(Exception.class)
	// public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
	// 	return new ResponseEntity<>("서버에서 오류가 발생했습니다. 나중에 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
	// }

	// @ExceptionHandler(NullPointerException.class)
	// public ResponseEntity<String> handleNullPointerException(NullPointerException ex, WebRequest request) {
	// 	return new ResponseEntity<>("널 포인터 예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	//
	// @ExceptionHandler(NotFoundException.class)
	// public ResponseEntity<String> handleNotFoundException(NotFoundException ex, WebRequest request) {
	// 	return new ResponseEntity<>("권한예외가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
	// }
}
