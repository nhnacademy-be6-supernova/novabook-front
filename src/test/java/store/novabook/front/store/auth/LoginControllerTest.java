package store.novabook.front.store.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class LoginControllerTest {

	@Test
	void testGetLoginForm() throws Exception {
		// Create an instance of LoginController
		LoginController controller = new LoginController();

		// Setup MockMvc to mock the Spring MVC infrastructure
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setViewResolvers(viewResolver())
			.build();

		// Perform GET request to "/login" endpoint and validate the response
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/auth/login_form")); // Expect view name to be "store/auth/login_form"
	}

	// Configure a view resolver for testing
	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
