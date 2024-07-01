package store.novabook.front.common;
//
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
//
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// import store.novabook.front.api.point.dto.response.GetPointPolicyResponse;
// import store.novabook.front.common.response.PageResponse;
//
// @RequestMapping("/api/v1/front/new-token")
// @Controller
// @RequiredArgsConstructor
// public class NewTokenController {
//
// 	@GetMapping
// 	public String getPointForm(HttpServletResponse response, HttpServletRequest request) {
//
// 		String originalRequestInfo = request.getHeader("Original-Request-Info");
// 		return "/login";
// 	}
// }


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.config.RefreshTokenContext;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/front")
@RequiredArgsConstructor
public class NewTokenController {

	private final RefreshTokenContext refreshTokenContext;
	private final MemberService memberService;

	@GetMapping("/new-token")
	public String getNewToken(HttpServletResponse response, HttpServletRequest request) {
		String refresh = request.getHeader("Refresh");
		GetNewTokenRequest getNewTokenRequest = new GetNewTokenRequest(refresh);
		GetNewTokenResponse getNewTokenResponse = memberService.newToken(getNewTokenRequest);

		refreshTokenContext.setSomeData(getNewTokenResponse.accessToken());

		return null;
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
