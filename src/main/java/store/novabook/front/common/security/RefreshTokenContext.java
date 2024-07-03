package store.novabook.front.common.security;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class RefreshTokenContext {

	private String tokenData;

	private String uri;
}
