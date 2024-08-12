//package store.novabook.front.store.cart.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.util.List;
//import java.util.Objects;
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
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import jakarta.servlet.http.Cookie;
//import store.novabook.front.api.cart.dto.CartBookListDTO;
//import store.novabook.front.api.cart.dto.response.CreateCartBookListResponse;
//import store.novabook.front.api.member.member.service.MemberAuthClient;
//import store.novabook.front.common.exception.ErrorCode;
//import store.novabook.front.common.exception.FeignClientException;
//import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;
//import store.novabook.front.common.util.CookieUtil;
//import store.novabook.front.store.cart.hash.RedisCartHash;
//import store.novabook.front.store.cart.service.CartService;
//import store.novabook.front.store.cart.service.RedisCartService;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@WebMvcTest(CartController.class)
//class CartControllerTest {
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
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//		currentMembersArgumentResolver = new CurrentMembersArgumentResolver(memberAuthClient);
//
//		mockMvc = MockMvcBuilders.standaloneSetup(new CartController(cartService, redisCartService))
//			.setCustomArgumentResolvers(currentMembersArgumentResolver)
//			.build();
//	}
//
//	@Test
//	void testGetCartBookAllGuestWithoutCookie() throws Exception {
//		MockHttpServletResponse response = mockMvc.perform(get("/carts"))
//			.andExpect(status().isOk())
//			.andExpect(view().name(CartController.STORE_CART_CART_LIST))
//			.andReturn()
//			.getResponse();
//
//		String uuid = Objects.requireNonNull(response.getCookie(CookieUtil.GUEST_COOKIE_NAME)).getValue();
//		assertNotNull(uuid); // UUID format 확인
//	}
//
//	@Test
//	void testGetCartBookAllGuestWithCookie() throws Exception {
//		String guestCookieValue = UUID.randomUUID().toString();
//		RedisCartHash redisCartHash = RedisCartHash.of(new CartBookListDTO());
//
//		when(redisCartService.getCartList(guestCookieValue)).thenReturn(redisCartHash);
//
//		mockMvc.perform(get("/carts")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue)))
//			.andExpect(status().isOk())
//			.andExpect(view().name(CartController.STORE_CART_CART_LIST))
//			.andExpect(model().attributeExists("cart"));
//	}
//
//	@Test
//	void testRefreshCartLoggedIn() throws Exception {
//		Long memberId = 1L;
//		CartBookListDTO cartBookListDTO = new CartBookListDTO();
//
//		when(cartService.getCartList()).thenReturn(cartBookListDTO);
//		doNothing().when(redisCartService).deleteCart(memberId);
//		doNothing().when(redisCartService).createCart(memberId);
//		doNothing().when(redisCartService).addCartBooks(memberId, cartBookListDTO);
//
//		mockMvc.perform(get("/carts/refresh")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, null)))
//			.andExpect(status().is3xxRedirection())
//			.andExpect(redirectedUrl("/carts"));
//	}
//
//	@Test
//	void testRefreshCartGuest() throws Exception {
//		String guestCookieValue = UUID.randomUUID().toString();
//		RedisCartHash redisCartHash = RedisCartHash.of(new CartBookListDTO());
//
//		when(redisCartService.getCartList(guestCookieValue)).thenReturn(redisCartHash);
//
//		mockMvc.perform(get("/carts/refresh")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue)))
//			.andExpect(status().is3xxRedirection())
//			.andExpect(redirectedUrl("/carts"));
//	}
//
//	@Test
//	void testDeleteCartBookLoggedIn() throws Exception {
//		Long memberId = 1L;
//		Long bookId = 1L;
//
//		doNothing().when(redisCartService).deleteCartBook(memberId, bookId);
//		doNothing().when(cartService).deleteCartBook(bookId);
//
//		mockMvc.perform(get("/carts/delete/{bookId}", bookId)
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, null)))
//			.andExpect(status().is3xxRedirection())
//			.andExpect(redirectedUrl("/carts"));
//	}
//
//	@Test
//	void testDeleteCartBookGuest() throws Exception {
//		String guestCookieValue = UUID.randomUUID().toString();
//		Long bookId = 1L;
//
//		doNothing().when(redisCartService).deleteCartBook(guestCookieValue, bookId);
//
//		mockMvc.perform(get("/carts/delete/{bookId}", bookId)
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue)))
//			.andExpect(status().is3xxRedirection())
//			.andExpect(redirectedUrl("/carts"));
//	}
//
//	@Test
//	void testGetCartBookAllLoggedInWithGuestCookieAndException() throws Exception {
//		Long memberId = 1L;
//		String guestCookieValue = UUID.randomUUID().toString();
//		RedisCartHash redisCartHash = RedisCartHash.of(new CartBookListDTO());
//
//		when(redisCartService.getCartList(guestCookieValue)).thenReturn(redisCartHash);
//		when(redisCartService.notExistCart(memberId)).thenReturn(true);
//		doNothing().when(redisCartService).createCart(memberId);
//		doNothing().when(redisCartService).addCartBooks(any(), any());
//		doNothing().when(redisCartService).deleteCart(guestCookieValue);
//		doThrow(new FeignClientException(500, ErrorCode.INTERNAL_SERVER_ERROR)).when(cartService).addCartBooks(any());
//		when(redisCartService.getCartList(memberId)).thenReturn(redisCartHash);
//
//		mockMvc.perform(get("/carts")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue)))
//			.andExpect(status().isOk())
//			.andExpect(view().name(CartController.STORE_CART_CART_LIST))
//			.andExpect(model().attributeExists("cart"));
//	}
//
//	@Test
//	void testGetCartBookAllLoggedInWithGuestCookieWithoutException() throws Exception {
//		Long memberId = 1L;
//		String guestCookieValue = UUID.randomUUID().toString();
//		RedisCartHash redisCartHash = RedisCartHash.of(new CartBookListDTO());
//		CartBookListDTO cartBookListDTO = new CartBookListDTO();
//
//		when(redisCartService.getCartList(guestCookieValue)).thenReturn(redisCartHash);
//		when(redisCartService.notExistCart(memberId)).thenReturn(true);
//		doNothing().when(redisCartService).createCart(memberId);
//		doNothing().when(redisCartService).addCartBooks(any(), any());
//		doNothing().when(redisCartService).deleteCart(guestCookieValue);
//		when(cartService.addCartBooks(any())).thenReturn(new CreateCartBookListResponse(List.of(1L, 2L, 3L)));
//		when(cartService.getCartList()).thenReturn(cartBookListDTO);
//
//		mockMvc.perform(get("/carts")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue)))
//			.andExpect(status().isOk())
//			.andExpect(view().name(CartController.STORE_CART_CART_LIST))
//			.andExpect(model().attribute("cart", redisCartHash.getCartBookList()));
//	}
//
//	@Test
//	void testGetCartBookAllGuestWithoutCookieAndWithExistingCart() throws Exception {
//		String guestCookieValue = UUID.randomUUID().toString();
//		RedisCartHash redisCartHash = RedisCartHash.of(new CartBookListDTO());
//
//		when(redisCartService.getCartList(guestCookieValue)).thenReturn(redisCartHash);
//
//		mockMvc.perform(get("/carts")
//				.cookie(new Cookie(CookieUtil.GUEST_COOKIE_NAME, guestCookieValue)))
//			.andExpect(status().isOk())
//			.andExpect(view().name(CartController.STORE_CART_CART_LIST))
//			.andExpect(model().attribute("cart", redisCartHash.getCartBookList()));
//	}
//
//
//}
