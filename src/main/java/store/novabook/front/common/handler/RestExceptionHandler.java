package store.novabook.front.common.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import store.novabook.front.common.exception.FeignClientException;

@ControllerAdvice(annotations = HandleWithAlert.class)
public class RestExceptionHandler {

	@ExceptionHandler(FeignClientException.class)
	public String handleSpecificFeignClientException(FeignClientException e, Model model) {
		model.addAttribute("errorMessage", e.getMessage());
		return "/error/error-alert";
	}
}
