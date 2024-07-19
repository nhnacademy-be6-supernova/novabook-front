// package store.novabook.front.api.order.naver;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.core.env.Environment;
// import store.novabook.front.common.util.KeyManagerUtil;
// import store.novabook.front.common.util.dto.NaverSearchDto;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.*;
//
// class BookSearchServiceTest {
//
// 	@Mock
// 	private NaverBookSearchApiClient naverBookSearchApiClient;
//
// 	@Mock
// 	private Environment environment;
//
// 	@InjectMocks
// 	private BookSearchService bookSearchService;
//
// 	@BeforeEach
// 	void setUp() {
// 		MockitoAnnotations.openMocks(this);
//
// 		NaverSearchDto naverSearchDto = new NaverSearchDto("dummyClientId", "dummyClientSecret");
// 		when(environment.getProperty(anyString())).thenReturn("dummyValue");
// 		mockStatic(KeyManagerUtil.class);
// 		when(KeyManagerUtil.getNaverConfig(environment)).thenReturn(naverSearchDto);
// 	}
//
// 	@Test
// 	void testSearchBooks() {
// 		// Given
// 		String query = "testQuery";
// 		String expectedResponse = "searchResult";
//
// 		when(naverBookSearchApiClient.getSearch(anyString(), anyString(), anyString(), anyInt(), anyInt()))
// 			.thenReturn(expectedResponse);
//
// 		// When
// 		String result = bookSearchService.searchBooks(query);
//
// 		// Then
// 		assertEquals(expectedResponse, result);
// 		verify(naverBookSearchApiClient, times(1))
// 			.getSearch(eq("dummyClientId"), eq("dummyClientSecret"), eq(query), eq(5), eq(1));
// 	}
// }
