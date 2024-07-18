package store.novabook.front.store.store;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StoreIndexControllerTest {

	@Test
	void testGetCategory() throws Exception {
		// Create an instance of StoreIndexController
		StoreIndexController controller = new StoreIndexController();

		// Setup MockMvc to mock the Spring MVC infrastructure
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
			.setViewResolvers(viewResolver())
			.build();

		// Perform GET request to "/store" endpoint and validate the response
		mockMvc.perform(get("/"))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/store_index")); // Expect view name to be "store/store_index"
	}

	// Configure a view resolver for testing
	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
