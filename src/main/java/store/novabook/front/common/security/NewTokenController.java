package store.novabook.front.common.security;

import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.service.MemberService;

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

	}

}
