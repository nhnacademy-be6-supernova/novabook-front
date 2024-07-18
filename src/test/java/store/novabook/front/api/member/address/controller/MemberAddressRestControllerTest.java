package store.novabook.front.api.member.address.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import store.novabook.front.api.member.address.dto.response.ExceedResponse;
import store.novabook.front.api.member.address.service.MemberAddressRestService;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@SpringBootTest
@AutoConfigureMockMvc
class MemberAddressRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberAddressRestService memberAddressRestService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new MemberAddressRestController(memberAddressRestService))
			.setCustomArgumentResolvers(new CurrentMembersArgumentResolver(memberAuthClient))
			.build();
	}

	@Test
	void testIsExceed() throws Exception {
		ExceedResponse mockResponse = new ExceedResponse(true);
		when(memberAddressRestService.isExceedMemberAddressCount()).thenReturn(mockResponse);

		mockMvc.perform(get("/api/v1/front/addresses/is-exceed")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));
	}
}

