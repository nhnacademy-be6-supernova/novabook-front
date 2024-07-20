package store.novabook.front.store.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

class UserControllerTest {

	@Test
	void testGetUserForm() throws Exception {
		UserController controller = new UserController();

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setViewResolvers(viewResolver())
			.build();

		mockMvc.perform(get("/users/user/form"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/auth/user_form"));
	}

	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
