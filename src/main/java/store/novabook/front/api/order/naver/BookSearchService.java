package store.novabook.front.api.order.naver;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import store.novabook.front.common.util.KeyManagerUtil;
import store.novabook.front.common.util.dto.NaverSearchDto;

@Service
@RequiredArgsConstructor
public class BookSearchService implements InitializingBean {

	private final NaverBookSearchApiClient naverBookSearchApiClient;
	private final Environment environment;

	private NaverSearchDto naverSearchDto;

	public String searchBooks(String query) {
		return naverBookSearchApiClient.getSearch(naverSearchDto.clientkey(), naverSearchDto.secretkey(), query, 5, 1);
	}

	@Override
	public void afterPropertiesSet() {
		RestTemplate restTemplate = new RestTemplate();
		this.naverSearchDto = KeyManagerUtil.getNaverConfig(environment, restTemplate);
	}
}
