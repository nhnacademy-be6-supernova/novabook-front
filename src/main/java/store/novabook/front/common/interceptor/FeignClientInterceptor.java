package store.novabook.front.common.interceptor;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

	private final HttpServletRequest request;
	private final HttpServletResponse response;

	@Override
	public void apply(RequestTemplate template) {

		Cookie[] cookies = request.getCookies();
		String access = response.getHeader("access");
		String refresh = response.getHeader("refresh");
		// access 가 있으면 그대로 씀
		if (Objects.nonNull(access)){
			template.header("Authorization", "Bearer " + access);
			template.header("Refresh", "Bearer " + refresh);
			return;
		}
		//cookies 에도 없으면 그냥 종료
		if (Objects.isNull(cookies)) {
			return;
		}
		//쿠키에 있으면 있있는거 그대로 씀
		Arrays.stream(cookies)
			.forEach(cookie -> {
				if ("Authorization".equals(cookie.getName())) {
					template.header("Authorization", "Bearer " + cookie.getValue());
				} else if ("Refresh".equals(cookie.getName())) {
					template.header("Refresh", "Bearer " + cookie.getValue());
				}
			});
	}
}