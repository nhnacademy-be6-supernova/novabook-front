package store.novabook.front.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import store.novabook.front.common.interceptor.FeignClientInterceptor;
import store.novabook.front.common.response.decorder.NovaErrorDecoder;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

	private final HttpServletRequest request;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new FeignClientInterceptor(request);
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new NovaErrorDecoder();
	}
}