package store.novabook.front.common.config;

import org.springframework.stereotype.Component;

@Component
public class RefreshTokenContext {

	private String someData;

	public String getSomeData() {
		return someData;
	}

	public void setSomeData(String someData) {
		this.someData = someData;
	}
}
