package store.novabook.front.common.config;

import java.util.Arrays;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

	private static final String COOKIE_HEADER = "Cookie";

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
