package store.novabook.front.common.handler;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.exceptions.TemplateInputException;

import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import store.novabook.front.common.exception.SeeOtherException;
import store.novabook.front.common.security.RefreshTokenContext;
import store.novabook.front.common.security.exception.InternalServerErrorException;

@ControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {

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

		return hasAuthorization && hasRefresh;
	}

	@ExceptionHandler({NullPointerException.class, InternalServerErrorException.class, SeeOtherException.class, FeignException.InternalServerError.class})
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

	@ExceptionHandler(FeignException.Unauthorized.class)
	public ResponseEntity<String> handleNullPointerException(FeignException.Unauthorized ex, WebRequest request) {
		return new ResponseEntity<>("리프레쉬토큰 끝남", HttpStatus.INTERNAL_SERVER_ERROR);
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
