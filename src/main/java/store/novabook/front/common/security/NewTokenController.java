package store.novabook.front.common.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.service.MemberService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/front")
@RequiredArgsConstructor
public class NewTokenController {

	private final RefreshTokenContext refreshTokenContext;
	private final MemberService memberService;

	@GetMapping("/new-token/{id}")
	public void getNewToken(@PathVariable(value = "id", required = false) String id, HttpServletRequest request, HttpServletResponse response) {
		String decodedJwt = java.net.URLDecoder.decode(id, StandardCharsets.UTF_8);
		String refresh = request.getHeader("Refresh");
		if (refresh == null || refresh.isEmpty()) {
			refresh = decodedJwt;
		}
		GetNewTokenRequest getNewTokenRequest = new GetNewTokenRequest(refresh);
		GetNewTokenResponse getNewTokenResponse = memberService.newToken(getNewTokenRequest);

		refreshTokenContext.setTokenData(getNewTokenResponse.accessToken());
		refreshTokenContext.setRefreshToken(refresh);

	}

	@PostMapping("/new-token/{id}")
	public void getNewToken1(@PathVariable(value = "id", required = false) String id, HttpServletRequest request, HttpServletResponse response) {
		String decodedJwt = java.net.URLDecoder.decode(id, StandardCharsets.UTF_8);
		String refresh = request.getHeader("Refresh");
		if (refresh == null || refresh.isEmpty()) {
			refresh = decodedJwt;
		}
		GetNewTokenRequest getNewTokenRequest = new GetNewTokenRequest(refresh);
		GetNewTokenResponse getNewTokenResponse = memberService.newToken(getNewTokenRequest);

		refreshTokenContext.setTokenData(getNewTokenResponse.accessToken());
		refreshTokenContext.setRefreshToken(refresh);

	}

	private Map<String, String> parseRequestInfo(String originalRequestInfo) {
		// JSON 파싱 (간단한 구현, 실제로는 JSON 파서 사용 권장)
		Map<String, String> requestInfo = new HashMap<>();
		String[] parts = originalRequestInfo.replace("{", "").replace("}", "").split(",");
		for (String part : parts) {
			String[] keyValue = part.split(":");
			requestInfo.put(keyValue[0].trim(), keyValue[1].trim());
		}
		return requestInfo;
	}

	private String requestNewAccessToken(String headers) {
		// 새로운 Access token 요청 (간단한 구현)
		RestTemplate restTemplate = new RestTemplate();
		// Refresh token을 사용하여 새로운 Access token 요청 로직 추가
		return "newAccessToken";
	}
}
