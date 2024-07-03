package store.novabook.front.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.common.interceptor.FeignClientInterceptor;
import store.novabook.front.common.security.RefreshTokenContext;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

	private final HttpServletRequest request;

	private final HttpServletResponse response;

	private final RefreshTokenContext refreshTokenContext;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new FeignClientInterceptor(request, response, refreshTokenContext);
	}

	// @Bean
	// public ErrorDecoder errorDecoder() {
	// 	return new CustomErrorDecoder();
	// }

}