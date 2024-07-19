package store.novabook.front.api.member.member.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import store.novabook.front.api.member.member.dto.request.LinkPaycoMembersUUIDRequest;
import store.novabook.front.api.member.member.service.PaycoApiClient;
import store.novabook.front.api.member.member.service.PaycoLoginClient;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.dto.PaycoResponseValidator;
import store.novabook.front.api.member.member.controller.PaycoOAuth2Controller;
import store.novabook.front.common.response.ApiResponse;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(PaycoOAuth2Controller.class)
public class PaycoOAuth2ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PaycoLoginClient paycoLoginClient;

	@MockBean
	private PaycoApiClient paycoApiClient;

	@MockBean
	private PaycoResponseValidator paycoResponseValidator;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@Test
	public void testPayco() throws Exception {
		mockMvc.perform(get("/oauth2/payco"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("https://id.payco.com/oauth2.0/authorize?"
				+ "response_type=code"
				+ "&client_id=3RD6nxfHUTIZ1sl7133gUN6"
				+ "&serviceProviderCode=FRIENDS"
				+ "&redirect_uri=https%3a%2f%2fwww.novabook.store%2foauth2%2fpayco%2fcallback"
				+ "&state=gh86qj"
				+ "&userLocale=ko_KR"))
			.andDo(print());
	}

	@Test
	public void testLinkPayco() throws Exception {
		mockMvc.perform(get("/oauth2/payco/link"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("https://id.payco.com/oauth2.0/authorize?"
				+ "response_type=code"
				+ "&client_id=3RD6nxfHUTIZ1sl7133gUN6"
				+ "&serviceProviderCode=FRIENDS"
				+ "&redirect_uri=https%3a%2f%2fwww.novabook.store%2foauth2%2fpayco%2flink%2fcallback"
				+ "&state=gh86qj"
				+ "&userLocale=ko_KR"))
			.andDo(print());
	}
}
