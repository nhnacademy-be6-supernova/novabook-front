package store.novabook.front.store.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

class LoginControllerTest {

	@Test
	void testGetLoginForm() throws Exception {
		LoginController controller = new LoginController();

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setViewResolvers(viewResolver())
			.build();

		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/auth/login_form"));
	}

	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
