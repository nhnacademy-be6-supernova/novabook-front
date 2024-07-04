package store.novabook.front.common.config;

import java.util.Objects;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.exceptions.TemplateInputException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import store.novabook.front.common.security.RefreshTokenContext;
import store.novabook.front.common.security.exception.InternalServerErrorException;
import store.novabook.front.common.security.exception.NotFoundException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final RefreshTokenContext refreshTokenContext;

	// @ExceptionHandler(Exception.class)
	// public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
	// 	return new ResponseEntity<>("서버에서 오류가 발생했습니다. 나중에 다시 시도해주세요.", HttpStatus.SEE_OTHER);
	// }


	//nav.html login, logout
	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		model.addAttribute("isLoggedIn", isLoggedIn(request));
	}

	private boolean isLoggedIn(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		boolean hasAuthorization = false;
		boolean hasRefresh = false;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("Authorization".equals(cookie.getName())) {
					hasAuthorization = true;
				}
				if ("Refresh".equals(cookie.getName())) {
					hasRefresh = true;
				}
			}
		}

		if (!hasAuthorization || !hasRefresh) {
			return false;
		}
		return true;
	}

	@ExceptionHandler({NullPointerException.class, InternalServerErrorException.class})
	public void handleNullPointerException(NullPointerException ex, WebRequest request) {
		if (Objects.nonNull(refreshTokenContext.getUri()) && Objects.nonNull(refreshTokenContext.getTokenData())) {
			return;
		}
		System.out.println();
	}

	@ExceptionHandler(TemplateInputException.class)
	public void handleTemplateInputException(TemplateInputException ex, WebRequest request) {
		if (Objects.nonNull(refreshTokenContext.getUri()) && Objects.nonNull(refreshTokenContext.getTokenData())) {
			return;
		}
		System.out.println();
	}

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
