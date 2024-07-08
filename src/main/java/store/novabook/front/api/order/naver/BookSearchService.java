package store.novabook.front.api.order.naver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import store.novabook.front.common.util.KeyManagerUtil;
import store.novabook.front.common.util.dto.NaverSearchDto;

@Service
public class BookSearchService {

	private final String clientId;
	private final String clientSecret;

	private final NaverBookSearchApiClient naverBookSearchApiClient;

	@Autowired
	public BookSearchService(NaverBookSearchApiClient naverBookSearchApiClient, Environment environment) {
		this.naverBookSearchApiClient = naverBookSearchApiClient;
		NaverSearchDto naver = KeyManagerUtil.getNaverConfig(environment);
		this.clientId = naver.clientkey();
		this.clientSecret = naver.secretkey();
	}

	public String searchBooks(String query) {
		return naverBookSearchApiClient.getSearch(clientId, clientSecret, query, 5, 1);
	}
}
