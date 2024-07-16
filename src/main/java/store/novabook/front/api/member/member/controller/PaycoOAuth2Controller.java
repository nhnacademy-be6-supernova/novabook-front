package store.novabook.front.api.member.member.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.api.member.member.dto.PaycoResponseValidator;
import store.novabook.front.api.member.member.dto.request.GetPaycoMembersRequest;
import store.novabook.front.api.member.member.dto.request.LinkPaycoMembersUUIDRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.response.GetPaycoMembersResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.PaycoApiClient;
import store.novabook.front.api.member.member.service.PaycoLoginClient;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.UnauthorizedException;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.util.KeyManagerUtil;
import store.novabook.front.common.util.dto.Oauth2Dto;

@Controller
@RequestMapping("/oauth2/payco")
public class PaycoOAuth2Controller {
	private final String clientId;
	private final String clientSecret;
	private final String redirectUri;

	private final PaycoLoginClient paycoLoginClient;
	private final PaycoApiClient paycoApiClient;
	private final PaycoResponseValidator paycoResponseValidator;
	private final MemberAuthClient memberAuthClient;

	public PaycoOAuth2Controller(PaycoLoginClient paycoLoginClient, PaycoApiClient paycoApiClient,
		PaycoResponseValidator paycoResponseValidator, MemberAuthClient memberAuthClient, Environment environment) {
		this.paycoLoginClient = paycoLoginClient;
		this.paycoApiClient = paycoApiClient;
		this.paycoResponseValidator = paycoResponseValidator;
		this.memberAuthClient = memberAuthClient;
		Oauth2Dto oauth2Dto = KeyManagerUtil.getOauth2Config(environment);
		this.clientId = oauth2Dto.clientId();
		this.clientSecret = oauth2Dto.clientSecret();
		this.redirectUri = oauth2Dto.redirectUri();
	}

	@GetMapping
	public void payco(HttpServletResponse response) {
		try {
			String redirectUrl = "https://id.payco.com/oauth2.0/authorize?"
				+ "response_type=code"
				+ "&client_id=3RD6nxfHUTIZ1sl7133gUN6"
				+ "&serviceProviderCode=FRIENDS"
				+ "&redirect_uri=https%3a%2f%2fwww.novabook.store%2foauth2%2fpayco%2fcallback"
				+ "&state=gh86qj"
				+ "&userLocale=ko_KR";
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			throw new RuntimeException("Redirection to OAuth2 provider failed", e);
		}
	}

	@GetMapping("/link")
	public void linkPayco(HttpServletResponse response) {
		try {
			String redirectUrl = "https://id.payco.com/oauth2.0/authorize?"
				+ "response_type=code"
				+ "&client_id=3RD6nxfHUTIZ1sl7133gUN6"
				+ "&serviceProviderCode=FRIENDS"
				+ "&redirect_uri=https%3a%2f%2fwww.novabook.store%2foauth2%2fpayco%2flink%2fcallback"
				+ "&state=gh86qj"
				+ "&userLocale=ko_KR";
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			throw new RuntimeException("Redirection to OAuth2 provider failed", e);
		}
	}

	@GetMapping("/link/callback")
	public String linkCallback(@RequestParam(value = "code", required = false) String code,
		@RequestParam(value = "state", required = false) String state,
		@RequestParam(value = "serviceExtra", required = false) String serviceExtraEncoded,
		HttpServletRequest request) {

		Map<String, Object> authorizationCode = paycoApiClient.getAuthorizationToken("authorization_code", clientId,
			clientSecret, code, redirectUri, state);

		String paycoAccessToken = (String)authorizationCode.get("access_token");
		if (Objects.isNull(paycoAccessToken)) {
			throw new RuntimeException("Failed to get Payco access token");
		}

		String paycoId = getPaycoId(paycoAccessToken);

		if (!logout(paycoAccessToken)) {
			throw new RuntimeException("Failed to logout");
		}

		Cookie[] cookies = request.getCookies();
		String accessToken = "";
		for (Cookie cookie : cookies) {
			if ("Authorization".equals(cookie.getName())) {
				accessToken = cookie.getValue();
			}
		}

		if (accessToken.isEmpty()) {
			throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
		}

		LinkPaycoMembersUUIDRequest linkPaycoMembersUUIDRequest = new LinkPaycoMembersUUIDRequest(accessToken, paycoId);

		ApiResponse<Void> paycoLinkResponse = memberAuthClient.paycoLink(linkPaycoMembersUUIDRequest);

		if (paycoLinkResponse == null) {
			return "redirect:/login";
		}

		return "redirect:/mypage";
	}

	@GetMapping("/callback")
	public String callback(@RequestParam(value = "code", required = false) String code,
		@RequestParam(value = "state", required = false) String state,
		@RequestParam(value = "serviceExtra", required = false) String serviceExtraEncoded,
		HttpServletResponse response) {

		Map<String, Object> authorizationCode = paycoApiClient.getAuthorizationToken("authorization_code", clientId,
			clientSecret, code, redirectUri, state);

		String paycoAccessToken = (String)authorizationCode.get("access_token");
		if (Objects.isNull(paycoAccessToken)) {
			throw new RuntimeException("Failed to get Payco access token");
		}

		String paycoId = getPaycoId(paycoAccessToken);

		if (!logout(paycoAccessToken)) {
			throw new RuntimeException("Failed to logout");
		}

		GetPaycoMembersRequest getPaycoMembersRequest = GetPaycoMembersRequest.builder()
			.paycoId(paycoId)
			.build();
		ApiResponse<GetPaycoMembersResponse> paycoMembersResponse = memberAuthClient.paycoLogin(
			getPaycoMembersRequest);

		if (paycoMembersResponse.getBody() == null) {
			return "redirect:/login";
		}

		String authorization = paycoMembersResponse.getBody().accessToken();
		String refresh = paycoMembersResponse.getBody().refreshToken();

		if (authorization != null && !authorization.isEmpty()) {
			String accessToken = authorization.replace("Bearer ", "");
			String refreshToken = refresh.replace("Bearer ", "");

			Cookie accessCookie = new Cookie("Authorization", accessToken);
			accessCookie.setMaxAge(60 * 60);
			accessCookie.setPath("/");
			response.addCookie(accessCookie);

			Cookie refreshCookie = new Cookie("Refresh", refreshToken);
			refreshCookie.setMaxAge(60 * 60 * 24);
			refreshCookie.setPath("/");
			response.addCookie(refreshCookie);

		} else {
			return "redirect:/login";
		}

		return "redirect:/";
	}

	@PostMapping("/payco/link/login")
	public String paycoLinkLogin(@ModelAttribute LoginMembersRequest loginMembersRequest,
		HttpServletResponse response) {

		return "redirect:/";
	}

	private String getPaycoId(String paycoAccessToken) {
		String response = paycoLoginClient.login(clientId, paycoAccessToken);
		return paycoResponseValidator.validateGetPaycoId(response).orElseThrow();
	}

	public boolean logout(String token) {

		String encodedToken = "";
		encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);

		String response = paycoApiClient.logout(clientId, clientSecret, encodedToken);
		return paycoResponseValidator.validateLogout(response);
	}

}
