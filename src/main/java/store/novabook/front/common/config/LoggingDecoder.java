package store.novabook.front.common.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import feign.Response;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.member.member.dto.response.LoginMemberResponse;

@Slf4j
public class LoggingDecoder implements Decoder {

	private final Decoder delegate;


	public LoggingDecoder(Decoder delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object decode(Response response, Type type) throws IOException {
		// 응답 본문을 로깅합니다.
		if(response.request().url().contains("auth/login")) {
			LoginMemberResponse result = (LoginMemberResponse) delegate.decode(response, LoginMemberResponse.class);
			return ResponseEntity.of(Optional.of(result));
		}
		String body = "";
		if (response.body() != null) {
			try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))) {
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
				}
				body = sb.toString();
				// 로깅
				System.out.println(body);
			}
		}

		// 새 Response 객체를 생성합니다.
		Response newResponse = response.toBuilder().body(body, StandardCharsets.UTF_8).build();

		// 응답 바디를 역직렬화
		Object result = delegate.decode(newResponse, type);

		return result;
	}
}

