package store.novabook.front.store.dormant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberDormantController.class)
@AutoConfigureMockMvc
class MemberDormantControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new MemberDormantController())
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testMemberDormant() throws Exception {
		mockMvc.perform(get("/dormant")) // Perform GET request to "/dormant"
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/dormant/dormant")); // Expect view name to be "store/dormant/dormant"
	}
}
