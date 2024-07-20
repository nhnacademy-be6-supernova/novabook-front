package store.novabook.front.api.member.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import store.novabook.front.api.member.member.dto.PaycoResponseValidator;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.PaycoApiClient;
import store.novabook.front.api.member.member.service.PaycoLoginClient;

@WebMvcTest(PaycoOAuth2Controller.class)
class PaycoOAuth2ControllerTest {

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
	void testPayco() throws Exception {
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
	void testLinkPayco() throws Exception {
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
