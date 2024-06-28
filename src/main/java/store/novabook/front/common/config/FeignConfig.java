package store.novabook.front.common.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

	private final HttpServletRequest request;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new FeignClientInterceptor(request);
	}
}