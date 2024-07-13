package store.novabook.front.common.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import store.novabook.front.common.exception.FeignClientException;
import store.novabook.front.common.exception.NovaException;
import store.novabook.front.common.exception.UnauthorizedException;

/**
 * {@code GlobalExceptionHandler} 클래스는 애플리케이션 전역에서 발생하는 예외를 처리하는 핸들러입니다.
 * 다양한 예외 유형에 대해 적절한 HTTP 상태 코드와 함께 응답을 반환합니다.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(NovaException.class)
	public String handleFeignClientException(FeignClientException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error/error";
	}

	@ExceptionHandler(UnauthorizedException.class)
	public String handleUnauthorizedException(UnauthorizedException exception) {
		return "redirect:/";
	}
}
