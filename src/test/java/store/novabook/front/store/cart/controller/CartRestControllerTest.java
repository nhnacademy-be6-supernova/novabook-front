//package store.novabook.front.store.cart.controller;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import jakarta.servlet.http.Cookie;
//import store.novabook.front.api.cart.dto.CartBookDTO;
//import store.novabook.front.api.cart.dto.CartBookIdDTO;
//import store.novabook.front.api.cart.dto.CartBookListDTO;
//import store.novabook.front.api.cart.dto.request.DeleteCartBookListRequest;
//import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
//import store.novabook.front.api.cart.dto.response.CreateCartBookListResponse;
//import store.novabook.front.api.cart.dto.response.CreateCartBookResponse;
//import store.novabook.front.api.member.member.service.MemberAuthClient;
//import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;
//import store.novabook.front.common.util.CookieUtil;
//import store.novabook.front.store.cart.hash.RedisCartHash;
//import store.novabook.front.store.cart.service.CartService;
//import store.novabook.front.store.cart.service.RedisCartService;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@WebMvcTest(CartRestController.class)
//class CartRestControllerTest {
//
//	@MockBean
//	private CartService cartService;
//
//	@MockBean
//	private RedisCartService redisCartService;
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private MemberAuthClient memberAuthClient;
//
//	@Mock
//	private CurrentMembersArgumentResolver currentMembersArgumentResolver;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//		currentMembersArgumentResolver = new CurrentMembersArgumentResolver(memberAuthClient);
//
//		mockMvc = MockMvcBuilders.standaloneSetup(new CartRestController(cartService, redisCartService))
//			.setCustomArgumentResolvers(currentMembersArgumentResolver)
//			.build();
//	}
//
//	@Test
//	void testAddCartBookLoggedInWithoutGuestCookie() throws Exception {
//		Long memberId = 1L;
//		CartBookDTO request = CartBookDTO.builder()
//			.bookId(1L)
//			.title("Test Book")
//			.price(1000L)
//			.discountPrice(900L)
//			.quantity(1)
//			.isPackaged(true)
//			.bookStatusId(1L)
//			.build();
//
//		when(redisCartService.notExistCart(memberId)).thenReturn(true);
//		doNothing().when(redisCartService).createCart(memberId);
//		doNothing().when(redisCartService).addCartBook(memberId, request);
//		CreateCartBookResponse response = new CreateCartBookResponse(new ArrayList<>());
//		when(cartService.addCartBook(any())).thenReturn(response);
//
//		mockMvc.perform(post("/api/v1/front/carts")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, null))
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testAddCartBookGuestWithoutCookie() throws Exception {
//		CartBookDTO request = CartBookDTO.builder()
//			.bookId(1L)
//			.title("Test Book")
//			.price(1000L)
//			.discountPrice(900L)
//			.quantity(1)
//			.isPackaged(true)
//			.bookStatusId(1L)
//			.build();
//
//		doNothing().when(redisCartService).createCart(anyString());
//		doNothing().when(redisCartService).addCartBook(anyString(), eq(request));
//
//		mockMvc.perform(post("/api/v1/front/carts")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testAddCartBookLoggedInWithGuestCookie() throws Exception {
//		Long memberId = 1L;
//		String guestCookieValue = UUID.randomUUID().toString();
//		CartBookDTO request = CartBookDTO.builder()
//			.bookId(1L)
//			.title("Test Book")
//			.price(1000L)
//			.discountPrice(900L)
//			.quantity(1)
//			.isPackaged(true)
//			.bookStatusId(1L)
//			.build();
//		RedisCartHash redisCartHash = RedisCartHash.of(new CartBookListDTO());
//
//		when(redisCartService.getCartList(guestCookieValue)).thenReturn(redisCartHash);
//		when(redisCartService.notExistCart(memberId)).thenReturn(true);
//		doNothing().when(redisCartService).createCart(memberId);
//		doNothing().when(redisCartService).addCartBooks(anyLong(), any(CartBookListDTO.class));
//		doNothing().when(redisCartService).deleteCart(guestCookieValue);
//		CreateCartBookResponse response = new CreateCartBookResponse(new ArrayList<>());
//		when(cartService.addCartBook(any())).thenReturn(response);
//		CreateCartBookListResponse response1 = new CreateCartBookListResponse(new ArrayList<>());
//		when(cartService.addCartBooks(any())).thenReturn(response1);
//
//		mockMvc.perform(post("/api/v1/front/carts")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue))
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testAddCartBookLoggedInWithGuestCookieAndNonEmptyCart() throws Exception {
//		Long memberId = 1L;
//		String guestCookieValue = UUID.randomUUID().toString();
//		CartBookDTO request = CartBookDTO.builder()
//			.bookId(1L)
//			.title("Test Book")
//			.price(1000L)
//			.discountPrice(900L)
//			.quantity(1)
//			.isPackaged(true)
//			.bookStatusId(1L)
//			.build();
//		List<CartBookDTO> cartBookList = new ArrayList<>();
//		cartBookList.add(request);
//		RedisCartHash redisCartHash = RedisCartHash.of(new CartBookListDTO(cartBookList));
//
//		when(redisCartService.getCartList(guestCookieValue)).thenReturn(redisCartHash);
//		when(redisCartService.notExistCart(memberId)).thenReturn(true);
//		doNothing().when(redisCartService).createCart(memberId);
//		doNothing().when(redisCartService).addCartBooks(anyLong(), any(CartBookListDTO.class));
//		doNothing().when(redisCartService).deleteCart(guestCookieValue);
//		CreateCartBookResponse response = new CreateCartBookResponse(new ArrayList<>());
//		when(cartService.addCartBook(any())).thenReturn(response);
//		CreateCartBookListResponse response1 = new CreateCartBookListResponse(new ArrayList<>());
//		when(cartService.addCartBooks(any())).thenReturn(response1);
//
//		mockMvc.perform(post("/api/v1/front/carts")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue))
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testDeleteCartBooksLoggedIn() throws Exception {
//		Long memberId = 1L;
//		DeleteCartBookListRequest request = new DeleteCartBookListRequest(List.of(1L, 2L, 3L));
//
//		doNothing().when(redisCartService).deleteCartBooks(memberId, request.bookIds());
//		doNothing().when(cartService).deleteCartBooks(request);
//
//		mockMvc.perform(delete("/api/v1/front/carts")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, null))
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testDeleteCartBooksGuest() throws Exception {
//		String guestCookieValue = UUID.randomUUID().toString();
//		DeleteCartBookListRequest request = new DeleteCartBookListRequest(List.of(1L, 2L, 3L));
//
//		doNothing().when(redisCartService).deleteCartBooks(guestCookieValue, request.bookIds());
//
//		mockMvc.perform(delete("/api/v1/front/carts")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue))
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testRefreshCartLoggedIn() throws Exception {
//		Long memberId = 1L;
//		CartBookIdDTO cartBookIdDTO = new CartBookIdDTO(Map.of());
//		CartBookListDTO cartBookListDTO = new CartBookListDTO();
//
//		when(cartService.getCartList()).thenReturn(cartBookListDTO);
//		doNothing().when(redisCartService).deleteCart(memberId);
//		doNothing().when(redisCartService).createCart(memberId);
//		doNothing().when(redisCartService).addCartBooks(memberId, cartBookListDTO);
//
//		mockMvc.perform(post("/api/v1/front/carts/refresh")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, null))
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(cartBookIdDTO)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testRefreshCartGuest() throws Exception {
//		String guestCookieValue = UUID.randomUUID().toString();
//		CartBookIdDTO cartBookIdDTO = new CartBookIdDTO(Map.of());
//		CartBookListDTO cartBookListDTO = new CartBookListDTO();
//
//		doNothing().when(redisCartService).deleteCart(guestCookieValue);
//		doNothing().when(redisCartService).createCart(guestCookieValue);
//		when(cartService.getCartListByGuest(cartBookIdDTO)).thenReturn(cartBookListDTO);
//		doNothing().when(redisCartService).addCartBooks(guestCookieValue, cartBookListDTO);
//
//		mockMvc.perform(post("/api/v1/front/carts/refresh")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue))
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(cartBookIdDTO)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testUpdateCartLoggedIn() throws Exception {
//		Long memberId = 1L;
//		UpdateCartBookQuantityRequest request = new UpdateCartBookQuantityRequest(1L, 2);
//
//		doNothing().when(cartService).updateCartBookQuantity(request);
//		doNothing().when(redisCartService).updateCartBookQuantity(memberId, request);
//
//		mockMvc.perform(put("/api/v1/front/carts/update")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, null))
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testUpdateCartGuest() throws Exception {
//		String guestCookieValue = UUID.randomUUID().toString();
//		UpdateCartBookQuantityRequest request = new UpdateCartBookQuantityRequest(1L, 2);
//
//		doNothing().when(redisCartService).updateCartBookQuantity(guestCookieValue, request);
//
//		mockMvc.perform(put("/api/v1/front/carts/update")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue))
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isOk());
//	}
//
//	@Test
//	void testUpdateCartNoMemberOrGuest() throws Exception {
//		UpdateCartBookQuantityRequest request = new UpdateCartBookQuantityRequest(1L, 2);
//
//		mockMvc.perform(put("/api/v1/front/carts/update")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(request)))
//			.andExpect(status().isBadRequest())
//			.andExpect(jsonPath("$.error").value("회원 또는 게스트 정보를 찾을 수 없습니다."));
//	}
//}
