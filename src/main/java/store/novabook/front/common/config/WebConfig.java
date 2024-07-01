package store.novabook.front.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.MemberClient;
import store.novabook.front.api.member.member.service.MemberService;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final GlobalContext globalContext;

	// private final MemberClient memberClient;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PointFormInterceptor(globalContext))
			.addPathPatterns("/admin/points/point/form"); // 인터셉터가 적용될 경로를 지정
	}
	// @Override
	// public void addInterceptors(InterceptorRegistry registry) {
	// 	registry.addInterceptor(new SeeOtherInterceptor());
	// }
}