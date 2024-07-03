package store.novabook.front.common.aop;

import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.service.MemberAuthClient;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final MemberAuthClient currentMembersClient;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(CurrentUser.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (request != null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if ("Authorization".equals(cookie.getName())) {
						String token = cookie.getValue();
						try {
							GetMembersUUIDRequest getMembersUUIDRequest = new GetMembersUUIDRequest("Bearer " + token);
							ResponseEntity<GetMembersUUIDResponse> uuid = currentMembersClient.uuid(getMembersUUIDRequest);
							return uuid.getBody().usersId();
						} catch (Exception e) {
							e.printStackTrace(); // 예외를 로그에 출력합니다.
							return null; // 예외 발생 시 null 반환 또는 적절한 에러 처리
						}
					}
				}
			}
		}
		return null;
	}
}
