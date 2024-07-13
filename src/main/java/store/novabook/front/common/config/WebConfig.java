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
import store.novabook.front.common.interceptor.LoginStatusInterceptor;
import store.novabook.front.common.interceptor.TokenInterceptor;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final ObjectProvider<MemberAuthClient> memberAuthClientProvider;
	private final ObjectProvider<MemberService> memberServices;


	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		MemberService memberService = memberServices.getIfAvailable();

		registry.addInterceptor(new TokenInterceptor(memberService))
			.excludePathPatterns(
				"/books",
				"/users/user/form/**",
				"**/categories",
				"/api/v1/front/new-token/**",
				"/login",
				"/",
				"**/favicon.ico",
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
		registry.addMapping(("/**"))
			.allowedOrigins("*")
			.allowedMethods(
				HttpMethod.GET.name(),
				HttpMethod.HEAD.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name());

	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CurrentMembersArgumentResolver(memberAuthClientProvider.getIfAvailable()));
	}
}
