package store.novabook.front.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import store.novabook.front.common.interceptor.WebClientInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

	private final HttpServletRequest request;

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.filter(new WebClientInterceptor(request))
			.filter((clientRequest, next) -> Mono.deferContextual(context -> {
				RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
				if (requestAttributes != null) {
					RequestContextHolder.setRequestAttributes(requestAttributes);
				}
				return next.exchange(clientRequest);
			}))
			.build();
	}
}
