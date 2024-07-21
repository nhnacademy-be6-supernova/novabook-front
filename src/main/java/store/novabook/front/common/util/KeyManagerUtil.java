package store.novabook.front.common.util;

import static store.novabook.front.common.exception.ErrorCode.*;

import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import store.novabook.front.common.exception.KeyManagerException;
import store.novabook.front.common.util.dto.NaverSearchDto;
import store.novabook.front.common.util.dto.Oauth2Dto;
import store.novabook.front.common.util.dto.RabbitMQConfigDto;
import store.novabook.front.common.util.dto.RedisConfigDto;

@Slf4j
public class KeyManagerUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	private KeyManagerUtil() {
	}

	@SuppressWarnings("checkstyle:LeftCurly")
	static String getDataSource(Environment environment, String keyid, RestTemplate restTemplate) {

		String appkey = environment.getProperty("nhn.cloud.keyManager.appkey");
		String userId = environment.getProperty("nhn.cloud.keyManager.userAccessKey");
		String secretKey = environment.getProperty("nhn.cloud.keyManager.secretAccessKey");

		String baseUrl = "https://api-keymanager.nhncloudservice.com/keymanager/v1.2/appkey/{appkey}/secrets/{keyid}";
		String url = baseUrl.replace("{appkey}", Objects.requireNonNull(appkey)).replace("{keyid}", keyid);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-TC-AUTHENTICATION-ID", userId);
		headers.set("X-TC-AUTHENTICATION-SECRET", secretKey);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
			new ParameterizedTypeReference<>() {
			});

		return getStringObjectMap(response);
	}

	private static String getStringObjectMap(ResponseEntity<Map<String, Object>> response) {
		if (response.getBody() == null) {
			throw new KeyManagerException(RESPONSE_BODY_IS_NULL);
		}
		Object bodyObj = response.getBody().get("body");

		Map<String, Object> body;
		try {
			body = TypeUtil.castMap(bodyObj, String.class, Object.class);
		} catch (ClassCastException e) {
			throw new KeyManagerException(MISSING_BODY_KEY);
		}

		String result = (String)body.get("secret");
		if (result == null || result.isEmpty()) {
			log.error("\"secret\" key is missing or empty in response body");
			log.error("{}", body);
			throw new KeyManagerException(MISSING_SECRET_KEY);
		}

		return result;
	}

	public static RedisConfigDto getRedisConfig(Environment environment, RestTemplate restTemplate) {
		try {
			String keyid = environment.getProperty("nhn.cloud.keyManager.redisKey");
			log.info("getRedisConfig, keyid: {}", keyid);
			return objectMapper.readValue(getDataSource(environment, keyid, restTemplate), RedisConfigDto.class);
		} catch (JsonProcessingException e) {
			//오류처리
			log.error("RedisConfig{}", FAILED_CONVERSION.getMessage());
			throw new KeyManagerException(FAILED_CONVERSION);
		}
	}

	public static NaverSearchDto getNaverConfig(Environment environment, RestTemplate restTemplate) {
		try {
			String keyid = environment.getProperty("nhn.cloud.keyManager.naverBookKey");
			return objectMapper.readValue(getDataSource(environment, keyid, restTemplate), NaverSearchDto.class);
		} catch (JsonProcessingException e) {
			//오류처리
			log.error("NaverSearch{}", FAILED_CONVERSION.getMessage());
			throw new KeyManagerException(FAILED_CONVERSION);
		}
	}

	public static Oauth2Dto getOauth2Config(Environment environment, RestTemplate restTemplate) {
		try {
			String keyid = environment.getProperty("nhn.cloud.keyManager.oauth2Key");
			return objectMapper.readValue(getDataSource(environment, keyid, restTemplate), Oauth2Dto.class);
		} catch (JsonProcessingException e) {
			log.error("Oauth2{}", FAILED_CONVERSION.getMessage());
			throw new KeyManagerException(FAILED_CONVERSION);
		}
	}

	public static RabbitMQConfigDto getRabbitMQConfig(Environment environment, RestTemplate restTemplate) {
		try {
			String keyid = environment.getProperty("nhn.cloud.keyManager.rabbitMQKey");
			return objectMapper.readValue(getDataSource(environment, keyid, restTemplate), RabbitMQConfigDto.class);
		} catch (JsonProcessingException e) {
			//오류처리
			log.error("RabbitMQConfig{}", FAILED_CONVERSION.getMessage());
			throw new KeyManagerException(FAILED_CONVERSION);
		}
	}

}
