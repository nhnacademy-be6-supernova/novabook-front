package store.novabook.front.common.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
// @RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

	@Autowired
	private HttpServletRequest request;

	@Override
	public void apply(RequestTemplate template) {
		Arrays.stream(request.getCookies())
			.filter(cookie -> "Authorization".equals(cookie.getName()))
			.findFirst()
			.ifPresent(cookie -> template.header("Authorization", "Bearer " + cookie.getValue()));
	}
}
