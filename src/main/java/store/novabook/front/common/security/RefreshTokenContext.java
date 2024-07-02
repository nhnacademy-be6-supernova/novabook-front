package store.novabook.front.common.security;

import org.springframework.stereotype.Component;

@Component
public class RefreshTokenContext {

	private String tokenData;

	public String getTokenData() {
		return tokenData;
	}

	public void setTokenData(String tokenData) {
		this.tokenData = tokenData;
	}
}
