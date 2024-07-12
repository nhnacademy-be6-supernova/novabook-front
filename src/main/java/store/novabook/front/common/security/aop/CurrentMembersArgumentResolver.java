package store.novabook.front.common.security.aop;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.UnauthorizedException;
import store.novabook.front.common.security.aop.dto.GetMembersTokenResponse;

@Slf4j
public class CurrentMembersArgumentResolver implements HandlerMethodArgumentResolver {

	private final MemberAuthClient memberAuthClient;

	public CurrentMembersArgumentResolver(MemberAuthClient memberAuthClient) {
		this.memberAuthClient = memberAuthClient;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentMembers.class) && parameter.getParameterType()
			.equals(Long.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		CurrentMembers currentMembers = parameter.getParameterAnnotation(CurrentMembers.class);
		boolean required = currentMembers != null && currentMembers.required();

		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (request == null) {
			if (required) {
				throw new UnauthorizedException(ErrorCode.NOT_HTTP_REQUEST);
			}
			return null;
		}

		Long membersId = getMemberIdFromCookies(request.getCookies());
		if (membersId == null && required) {
			throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
		}

		return membersId;
	}

	private Long getMemberIdFromCookies(Cookie[] cookies) throws Exception {
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if ("Authorization".equals(cookie.getName())) {
				try {
					ResponseEntity<GetMembersTokenResponse> response = memberAuthClient.token();
					return Objects.requireNonNull(response.getBody()).membersId();
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return null;
	}
}

