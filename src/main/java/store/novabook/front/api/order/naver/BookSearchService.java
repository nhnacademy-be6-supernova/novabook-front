package store.novabook.front.api.order.naver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import store.novabook.front.common.util.KeyManagerUtil;
import store.novabook.front.common.util.dto.NaverSearchDto;

@Service
public class BookSearchService {

	private final String clientId;
	private final String clientSecret;

	private final NaverBookSearchApiClient naverBookSearchApiClient;

	@Autowired
	public BookSearchService(NaverBookSearchApiClient naverBookSearchApiClient, Environment environment) {
		RestTemplate restTemplate = new RestTemplate();
		this.naverBookSearchApiClient = naverBookSearchApiClient;
		NaverSearchDto naver = KeyManagerUtil.getNaverConfig(environment, restTemplate);
		this.clientId = naver.clientkey();
		this.clientSecret = naver.secretkey();
	}

	public String searchBooks(String query) {
		return naverBookSearchApiClient.getSearch(clientId, clientSecret, query, 5, 1);
	}
}
