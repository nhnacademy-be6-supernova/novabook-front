package store.novabook.front.common.config;

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
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("Authorization".equals(cookie.getName())) {
					String token = cookie.getValue();
					template.header("Authorization", "Bearer " + token);
					break;
				}
			}
		}
	}
}
