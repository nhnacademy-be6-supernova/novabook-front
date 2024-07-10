package store.novabook.front.common.config;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.api.member.member.service.TestService;
import store.novabook.front.common.interceptor.LoginStatusInterceptor;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;
import store.novabook.front.common.interceptor.TokenInterceptor;
import store.novabook.front.common.security.RefreshTokenContext;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final RefreshTokenContext refreshTokenContext;
	private final ObjectProvider<MemberAuthClient> memberAuthClientProvider;
	private final ObjectProvider<MemberService> memberServices; // 변경된 부분


	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		MemberService memberService = memberServices.getIfAvailable(); // 변경된 부분

		registry.addInterceptor(new TokenInterceptor(refreshTokenContext, memberService))
			.excludePathPatterns(
				"/books",
				// "/carts",
				"/login",
				"/users/user/form/**",
				"**/favicon.ico",
				"**/categories",
				// "/auth/**",
				"/",
				"/api/v1/front/new-token/**",
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
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("https://www.novabook.store")  // 특정 도메인을 명시적으로 설정
			.allowedOrigins("https://novabook.store")  // 특정 도메인을 명시적으로 설정
			.allowedOrigins("http://localhost:8080")  // 특정 도메인을 명시적으로 설정
			.allowedMethods(
				HttpMethod.HEAD.name(),
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name())
			.allowCredentials(true);  // 자격 증명을 허용
	}

	// @Override
	// public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
	// 	resolvers.add(new CurrentMembersArgumentResolver(memberAuthClientProvider.getIfAvailable()));
	// }
}
