package store.novabook.front.common.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

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
	// public Decoder feignDecoder() {
	// 	return new LoggingDecoder(new SpringDecoder(messageConverters));
	// }

}