package store.novabook.front.common.security.aop;

import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import store.novabook.front.api.member.member.service.MemberAuthClient;

public class CurrentMembersArgumentResolver implements HandlerMethodArgumentResolver {

	private final MemberAuthClient memberAuthClient;

	public CurrentMembersArgumentResolver(MemberAuthClient memberAuthClient) {
		this.memberAuthClient = memberAuthClient;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentMembers.class) && parameter.getParameterType().equals(Long.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (request != null) {
			Long membersId;
			Cookie[] cookies = request.getCookies();
			if(cookies == null) return null;
			for (Cookie cookie : cookies) {
				if ("Authorization".equals(cookie.getName())) {
					try {
						ResponseEntity<GetMembersTokenResponse> response = memberAuthClient.token();
						membersId = response.getBody().membersId();
						return membersId;
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return null;
	}
}

