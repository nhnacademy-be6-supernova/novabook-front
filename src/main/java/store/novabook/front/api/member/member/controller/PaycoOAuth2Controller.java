package store.novabook.front.api.member.member.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.PaycoResponseValidator;
import store.novabook.front.api.member.member.dto.request.GetPaycoMembersRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.response.GetPaycoMembersResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.PaycoApiClient;
import store.novabook.front.api.member.member.service.PaycoLoginClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth2/payco")
public class PaycoOAuth2Controller {

	@Value("${oauth2.client-id}")
	private String clientId;

	@Value("${oauth2.client-secret}")
	private String clientSecret;

	@Value("${oauth2.redirect-uri}")
	private String redirectUri;

	private final PaycoLoginClient paycoLoginClient;
	private final PaycoApiClient paycoApiClient;
	private final PaycoResponseValidator paycoResponseValidator;
	private final MemberAuthClient memberAuthClient;

	@GetMapping
	public void payco(HttpServletResponse response) {
		try {
			String redirectUrl = "https://id.payco.com/oauth2.0/authorize?"
				+ "response_type=code"
				+ "&client_id=3RD6nxfHUTIZ1sl7133gUN6"
				+ "&serviceProviderCode=FRIENDS"
				+ "&redirect_uri=http%3a%2f%2ftest.com%3a8080%2foauth2%2fpayco%2fcallback"
				+ "&state=gh86qj"
				+ "&userLocale=ko_KR";
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			throw new RuntimeException("Redirection to OAuth2 provider failed", e);
		}
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

		//afaf22a0-beb4-11e6-bc03-005056ac229d
		GetPaycoMembersRequest getPaycoMembersRequest = GetPaycoMembersRequest.builder()
			.paycoId(paycoId)
			.build();
		ResponseEntity<GetPaycoMembersResponse> paycoMembersResponse = memberAuthClient.paycoLogin(getPaycoMembersRequest);

		if (!paycoMembersResponse.getStatusCode().is2xxSuccessful()) {
			return "redirect:/login";
		}
		if(paycoMembersResponse.getBody() == null) {
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
