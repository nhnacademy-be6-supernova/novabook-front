package store.novabook.front.store.cart;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import store.novabook.front.api.cart.CartClient;
import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookIdDTO;
import store.novabook.front.api.cart.dto.CartBookListDTO;
import store.novabook.front.api.cart.dto.request.DeleteCartBookListRequest;
import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
import store.novabook.front.api.cart.dto.response.CreateCartBookListResponse;
import store.novabook.front.api.cart.dto.response.CreateCartBookResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.store.cart.service.CartService;

class CartServiceTest {

	private CartService cartService;
	private CartClient cartClient;

	@BeforeEach
	void setUp() {
		cartClient = mock(CartClient.class);
		cartService = new CartService(cartClient);
	}

	@Test
	void testAddCartBook() {
		CartBookDTO cartBookDTO = CartBookDTO.builder()
			.bookId(1L)
			.title("Effective Java")
			.image("http://example.com/effective_java.jpg")
			.price(4500L)
			.discountPrice(400L)
			.quantity(5)
			.isPackaged(true)
			.bookStatusId(2L)
			.build();
		CreateCartBookResponse expectedResponse = new CreateCartBookResponse(Arrays.asList(1L));

		when(cartClient.addCartBook(cartBookDTO)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		CreateCartBookResponse actualResponse = cartService.addCartBook(cartBookDTO);

		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	void testAddCartBooks() {
		List<CartBookDTO> cartBookDTOList = List.of(
			new CartBookDTO(1L, "Effective Java", "http://example.com/effective_java.jpg", 4500L, 400L, 5, true, 2L),
			new CartBookDTO(2L, "Clean Code", "http://example.com/clean_code.jpg", 5500L, 500L, 3, false, 3L)
		);
		CartBookListDTO cartBookListDTO = new CartBookListDTO(cartBookDTOList);
		CreateCartBookListResponse expectedResponse = new CreateCartBookListResponse(Arrays.asList(1L));

		when(cartClient.addCartBooks(cartBookListDTO)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		CreateCartBookListResponse actualResponse = cartService.addCartBooks(cartBookListDTO);

		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	void testGetCartList() {
		List<CartBookDTO> cartBookDTOList = List.of(
			new CartBookDTO(1L, "Effective Java", "http://example.com/effective_java.jpg", 4500L, 400L, 5, true, 2L),
			new CartBookDTO(2L, "Clean Code", "http://example.com/clean_code.jpg", 5500L, 500L, 3, false, 3L)
		);
		CartBookListDTO cartBookListDTO = new CartBookListDTO(cartBookDTOList);

		when(cartClient.getCartBookAllByMemberId()).thenReturn(new ApiResponse<>("SUCCESS", true, cartBookListDTO));

		CartBookListDTO actualResponse = cartService.getCartList();

		assertEquals(cartBookListDTO, actualResponse);
	}

	@Test
	void testDeleteCartBook() {
		Long bookId = 1L;

		cartService.deleteCartBook(bookId);

		verify(cartClient, times(1)).deleteCartBook(bookId);
	}

	@Test
	void testDeleteCartBooks() {
		DeleteCartBookListRequest request = new DeleteCartBookListRequest(List.of(1L, 2L));

		cartService.deleteCartBooks(request);

		verify(cartClient, times(1)).deleteCartBooks(request);
	}

	@Test
	void testUpdateCartBookQuantity() {
		UpdateCartBookQuantityRequest request = new UpdateCartBookQuantityRequest(1L, 5);

		cartService.updateCartBookQuantity(request);

		verify(cartClient, times(1)).updateCartBook(request);
	}

	@Test
	void testGetCartListByGuest() {
		CartBookIdDTO cartBookIdDTO = new CartBookIdDTO(Map.of(
			1L, 5,
			2L, 3
		));
		List<CartBookDTO> cartBookDTOList = List.of(
			new CartBookDTO(1L, "Effective Java", "http://example.com/effective_java.jpg", 4500L, 400L, 5, true, 2L),
			new CartBookDTO(2L, "Clean Code", "http://example.com/clean_code.jpg", 5500L, 500L, 3, false, 3L)
		);
		CartBookListDTO cartBookListDTO = new CartBookListDTO(cartBookDTOList);

		when(cartClient.getCartBookAllByGuest(cartBookIdDTO)).thenReturn(new ApiResponse<>("SUCCESS", true, cartBookListDTO));

		CartBookListDTO actualResponse = cartService.getCartListByGuest(cartBookIdDTO);

		assertEquals(cartBookListDTO, actualResponse);
	}
}
