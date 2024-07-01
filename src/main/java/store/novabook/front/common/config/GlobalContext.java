package store.novabook.front.common.config;

import org.springframework.stereotype.Component;

@Component
public class GlobalContext {

	// 필요한 데이터를 저장하는 필드
	private String someData;

	public String getSomeData() {
		return someData;
	}

	public void setSomeData(String someData) {
		this.someData = someData;
	}
}
