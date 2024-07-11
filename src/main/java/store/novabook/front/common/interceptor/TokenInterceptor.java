package store.novabook.front.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.security.RefreshTokenContext;

@Slf4j
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

	private final RefreshTokenContext refreshTokenContext;
	private final MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		if (!request.getRequestURI().equals("/error")
			&& !request.getRequestURI().equals("/admin")) {
			refreshTokenContext.setUri(request.getRequestURI());
		}
		return true;
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {

	}
}
