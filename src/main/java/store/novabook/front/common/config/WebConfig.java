package store.novabook.front.common.config;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.interceptor.LoginStatusInterceptor;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;
import store.novabook.front.common.interceptor.TokenInterceptor;
import store.novabook.front.common.security.RefreshTokenContext;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final RefreshTokenContext refreshTokenContext;
	private final ObjectProvider<MemberAuthClient> memberAuthClientProvider;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TokenInterceptor(refreshTokenContext))
			.excludePathPatterns(
				"/books",
				// "/carts",
				"/login",
				"/users/user/form/**",
				// "/auth/**",
				"/",
				"/api/v1/front/new-token",
				"/**/*.css",
				"/**/*.html",
				"/**/*.js",
				"/**/*.png",
				"/**/*.jpg",
				"/**/*.jpeg",
				"/**/*.gif"
			);
		registry.addInterceptor(new LoginStatusInterceptor())
			.addPathPatterns("/users/user/form", "/login");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CurrentMembersArgumentResolver(memberAuthClientProvider.getIfAvailable()));
	}
}
