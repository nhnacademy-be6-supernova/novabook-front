package store.novabook.front.store.store;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;

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

import store.novabook.front.api.book.dto.response.GetBookToMainResponse;
import store.novabook.front.api.book.dto.response.GetBookToMainResponseMap;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StoreIndexController.class)
@AutoConfigureMockMvc
class StoreIndexControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new StoreIndexController(bookService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testIndex() throws Exception {

		GetBookToMainResponse bookResponse = new GetBookToMainResponse(
			1L,
			"Effective Java",
			"path/to/image.jpg",
			45000,
			40500
		);

		GetBookToMainResponseMap mockResponseMap = mock(GetBookToMainResponseMap.class);
		when(mockResponseMap.mainBookData()).thenReturn(Map.of(
			"newBooks", List.of(bookResponse),
			"popularBooks", List.of(bookResponse)
		));
		when(bookService.getBookToMainPage()).thenReturn(mockResponseMap);

		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/store_index"))
			.andExpect(model().attributeExists("newBooks"))
			.andExpect(model().attributeExists("popularBooks"))
			.andExpect(model().attribute("newBooks", List.of(bookResponse)))
			.andExpect(model().attribute("popularBooks", List.of(bookResponse)));

		verify(bookService, times(1)).getBookToMainPage();
		verifyNoMoreInteractions(bookService);
	}
}
