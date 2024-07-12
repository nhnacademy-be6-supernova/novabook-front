package store.novabook.front.common.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import store.novabook.front.common.exception.UnauthorizedException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UnauthorizedException.class)
	public String handleUnauthorizedException(UnauthorizedException exception) {
		return "redirect:/login";
	}
}
