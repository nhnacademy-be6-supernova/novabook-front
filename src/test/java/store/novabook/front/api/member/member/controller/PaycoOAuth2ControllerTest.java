// package store.novabook.front.api.member.member.controller;
//
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.*;
//
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;
//
// import jakarta.servlet.http.Cookie;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.core.env.Environment;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;
//
// import store.novabook.front.api.member.member.dto.PaycoResponseValidator;
// import store.novabook.front.api.member.member.dto.request.LinkPaycoMembersUUIDRequest;
// import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
// import store.novabook.front.api.member.member.dto.response.GetPaycoMembersResponse;
// import store.novabook.front.api.member.member.service.MemberAuthClient;
// import store.novabook.front.api.member.member.service.PaycoApiClient;
// import store.novabook.front.api.member.member.service.PaycoLoginClient;
// import store.novabook.front.common.exception.UnauthorizedException;
// import store.novabook.front.common.response.ApiResponse;
// import store.novabook.front.common.util.KeyManagerUtil;
// import store.novabook.front.common.util.dto.Oauth2Dto;
//
// import org.springframework.test.web.servlet.MockMvc;
//
// public class PaycoOAuth2ControllerTest {
//
// 	@Mock
// 	private PaycoLoginClient paycoLoginClient;
//
// 	@Mock
// 	private PaycoApiClient paycoApiClient;
//
// 	@Mock
// 	private PaycoResponseValidator paycoResponseValidator;
//
// 	@Mock
// 	private MemberAuthClient memberAuthClient;
//
// 	@Mock
// 	private Environment environment;
//
// 	@InjectMocks
// 	private PaycoOAuth2Controller paycoOAuth2Controller;
//
// 	private MockMvc mockMvc;
//
// 	@BeforeEach
// 	void setUp() {
// 		MockitoAnnotations.openMocks(this);
// 		Oauth2Dto oauth2Dto = new Oauth2Dto("clientId", "clientSecret", "redirectUri");
// 		when(KeyManagerUtil.getOauth2Config(environment)).thenReturn(oauth2Dto);
//
// 		mockMvc = MockMvcBuilders.standaloneSetup(paycoOAuth2Controller).build();
// 	}
//
// 	@Test
// 	void payco_ShouldRedirectToOAuth2Provider() throws Exception {
// 		mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/payco"))
// 			.andDo(MockMvcResultHandlers.print())
// 			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
// 	}
//
// 	@Test
// 	void linkPayco_ShouldRedirectToOAuth2Provider() throws Exception {
// 		mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/payco/link"))
// 			.andDo(MockMvcResultHandlers.print())
// 			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
// 	}
//
// 	@Test
// 	void linkCallback_ShouldLinkPaycoAccount() throws Exception {
// 		Map<String, Object> authorizationCode = new HashMap<>();
// 		authorizationCode.put("access_token", "paycoAccessToken");
// 		when(paycoApiClient.getAuthorizationToken(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
// 			.thenReturn(authorizationCode);
//
// 		when(paycoResponseValidator.validateGetPaycoId(anyString())).thenReturn(java.util.Optional.of("paycoId"));
//
// 		HttpServletRequest request = mock(HttpServletRequest.class);
// 		Cookie[] cookies = new Cookie[1];
// 		cookies[0] = new Cookie("Authorization", "accessToken");
// 		when(request.getCookies()).thenReturn(cookies);
//
// 		when(memberAuthClient.paycoLink(any(LinkPaycoMembersUUIDRequest.class))).thenReturn(new ApiResponse<>());
//
// 		mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/payco/link/callback")
// 				.param("code", "code")
// 				.param("state", "state")
// 				.param("serviceExtra", "serviceExtra"))
// 			.andDo(MockMvcResultHandlers.print())
// 			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
// 			.andExpect(MockMvcResultMatchers.redirectedUrl("/mypage"));
// 	}
//
// 	@Test
// 	void callback_ShouldAuthenticateWithPayco() throws Exception {
// 		Map<String, Object> authorizationCode = new HashMap<>();
// 		authorizationCode.put("access_token", "paycoAccessToken");
// 		when(paycoApiClient.getAuthorizationToken(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
// 			.thenReturn(authorizationCode);
//
// 		when(paycoResponseValidator.validateGetPaycoId(anyString())).thenReturn(java.util.Optional.of("paycoId"));
//
// 		GetPaycoMembersResponse getPaycoMembersResponse = mock(GetPaycoMembersResponse.class);
// 		when(getPaycoMembersResponse.accessToken()).thenReturn("Bearer accessToken");
// 		when(getPaycoMembersResponse.refreshToken()).thenReturn("Bearer refreshToken");
//
// 		ApiResponse<GetPaycoMembersResponse> apiResponse = new ApiResponse<>();
// 		apiResponse.setBody(getPaycoMembersResponse);
// 		when(memberAuthClient.paycoLogin(any())).thenReturn(apiResponse);
//
// 		mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/payco/callback")
// 				.param("code", "code")
// 				.param("state", "state")
// 				.param("serviceExtra", "serviceExtra"))
// 			.andDo(MockMvcResultHandlers.print())
// 			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
// 			.andExpect(MockMvcResultMatchers.redirectedUrl("/"));
// 	}
//
// 	@Test
// 	void callback_ShouldRedirectToLoginWhenAuthenticationFails() throws Exception {
// 		Map<String, Object> authorizationCode = new HashMap<>();
// 		authorizationCode.put("access_token", "paycoAccessToken");
// 		when(paycoApiClient.getAuthorizationToken(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
// 			.thenReturn(authorizationCode);
//
// 		when(paycoResponseValidator.validateGetPaycoId(anyString())).thenReturn(java.util.Optional.of("paycoId"));
//
// 		ApiResponse<GetPaycoMembersResponse> apiResponse = new ApiResponse<>();
// 		apiResponse.setBody(null);
// 		when(memberAuthClient.paycoLogin(any())).thenReturn(apiResponse);
//
// 		mockMvc.perform(MockMvcRequestBuilders.get("/oauth2/payco/callback")
// 				.param("code", "code")
// 				.param("state", "state")
// 				.param("serviceExtra", "serviceExtra"))
// 			.andDo(MockMvcResultHandlers.print())
// 			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
// 			.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
// 	}
//
// 	@Test
// 	void paycoLinkLogin_ShouldRedirectToHome() throws Exception {
// 		LoginMembersRequest loginMembersRequest = new LoginMembersRequest();
// 		mockMvc.perform(MockMvcRequestBuilders.post("/oauth2/payco/payco/link/login")
// 				.flashAttr("loginMembersRequest", loginMembersRequest))
// 			.andDo(MockMvcResultHandlers.print())
// 			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
// 			.andExpect(MockMvcResultMatchers.redirectedUrl("/"));
// 	}
// }
