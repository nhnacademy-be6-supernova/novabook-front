package store.novabook.front.admin.admin;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.admin.service.AdminService;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@WebMvcTest(AdminIndexController.class)
class AdminIndexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService adminService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminIndexController(adminService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testIndex() throws Exception {
		mockMvc.perform(get("/admin"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/admin_index"))
			.andDo(MockMvcResultHandlers.print());

		verify(adminService).admin();
	}
}
