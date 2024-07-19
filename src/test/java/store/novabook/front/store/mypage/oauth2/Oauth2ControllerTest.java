package store.novabook.front.store.mypage.oauth2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class Oauth2ControllerTest {

	@InjectMocks
	private Oauth2Controller oauth2Controller;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(oauth2Controller).build();
	}

	@Test
	void testOauth2() throws Exception {
		// Perform GET request to "/mypage/oauth2"
		mockMvc.perform(get("/mypage/oauth2"))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/mypage/oauth2/oauth2")); // Expect view name
	}
}
