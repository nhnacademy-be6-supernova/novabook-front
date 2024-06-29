package store.novabook.front.common.config;

import feign.RequestInterceptor;
import feign.codec.Decoder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

	private final HttpServletRequest request;

	private final ObjectFactory<HttpMessageConverters> messageConverters;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new FeignClientInterceptor(request);
	}

	@Bean
	public Decoder feignDecoder() {
		return new LoggingDecoder(new SpringDecoder(messageConverters));
	}

}