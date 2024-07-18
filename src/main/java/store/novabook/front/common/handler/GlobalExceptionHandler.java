package store.novabook.front.common.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import store.novabook.front.common.exception.BadGatewayException;
import store.novabook.front.common.exception.ForbiddenException;
import store.novabook.front.common.exception.InternalServerException;
import store.novabook.front.common.exception.NovaException;
import store.novabook.front.common.exception.UnauthorizedException;
import store.novabook.front.common.security.exception.AlreadyLoginException;

/**
 * {@code GlobalExceptionHandler} 클래스는 애플리케이션 전역에서 발생하는 예외를 처리하는 핸들러입니다.
 * 다양한 예외 유형에 대해 적절한 HTTP 상태 코드와 함께 응답을 반환합니다.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(AlreadyLoginException.class)
	public String handleAlreadyLoginException(AlreadyLoginException e) {
		return "redirect:/";
	}

	@ExceptionHandler(UnauthorizedException.class)
	public String handleUnauthorizedException(UnauthorizedException exception) {
		return "redirect:/";
	}

	@ExceptionHandler(ForbiddenException.class)
	public String handleForbiddenException(ForbiddenException exception, Model model) {
		model.addAttribute("exception", exception);
		return "error/403";
	}

	@ExceptionHandler(InternalServerException.class)
	public String handleInternalServerException(InternalServerException e, Model model) {
		model.addAttribute("exception", e);
		return "error/500";
	}

	@ExceptionHandler(BadGatewayException.class)
	public String handleBadGatewayException(BadGatewayException e, Model model) {
		model.addAttribute("exception", e);
		return "error/502";
	}

	@ExceptionHandler(NovaException.class)
	public String handleFeignClientException(NovaException e, Model model) {
		model.addAttribute("exception", e);
		return "error/nova_error";
	}

}
