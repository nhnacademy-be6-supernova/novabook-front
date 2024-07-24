package store.novabook.front.common.interceptor;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientInterceptor implements ExchangeFilterFunction {

	private final HttpServletRequest request;

	private static final String AUTHORIZATION = "Authorization";
	private static final String REFRESH = "Refresh";
	private static final String BEARER = "Bearer";

	@Override
	public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction next) {
		ClientRequest.Builder requestBuilder = ClientRequest.from(clientRequest);

		String reissuedAccessToken = (String) this.request.getAttribute("reissuedAccessToken");
		Cookie[] cookies = this.request.getCookies();
		if (Objects.isNull(cookies)) {
			return next.exchange(clientRequest);
		}

		Arrays.stream(cookies).forEach(cookie -> {
			if (AUTHORIZATION.equals(cookie.getName())) {
				if (reissuedAccessToken != null) {
					requestBuilder.header(AUTHORIZATION, BEARER + " " + reissuedAccessToken);
				} else {
					requestBuilder.header(AUTHORIZATION, BEARER + " " + cookie.getValue());
				}
			} else if (REFRESH.equals(cookie.getName())) {
				requestBuilder.header(REFRESH, BEARER + " " + cookie.getValue());
			}
		});

		return next.exchange(requestBuilder.build());
	}
}
