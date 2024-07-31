package store.novabook.front.store.cart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookListDTO;
import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
import store.novabook.front.store.cart.hash.RedisCartHash;
import store.novabook.front.store.cart.repository.RedisCartRepository;

class RedisCartServiceTest {

	@Mock
	private RedisCartRepository redisCartRepository;

	@InjectMocks
	private RedisCartService redisCartService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}


	@Test
	void getCartList_CartExists() {
		Object cartId = "testCartId";
		RedisCartHash redisCartHash = RedisCartHash.of(cartId);
		redisCartHash.update(new ArrayList<>());
		when(redisCartRepository.findById(cartId)).thenReturn(Optional.of(redisCartHash));
		RedisCartHash result = redisCartService.getCartList(cartId);
		assertNotNull(result);
		assertEquals(cartId, result.getCartId());
	}

	@Test
	void getCartList_CartDoesNotExist() {
		Object cartId = "testCartId";
		when(redisCartRepository.findById(cartId)).thenReturn(Optional.empty());
		RedisCartHash result = redisCartService.getCartList(cartId);
		assertNotNull(result);
		assertEquals(cartId, result.getCartId());
		assertTrue(result.getCartBookList().isEmpty());
	}

	@Test
	void addCartBook_NewBook() {
		Object cartId = "testCartId";
		CartBookDTO cartBookDTO = CartBookDTO.builder()
			.bookId(1L)
			.title("test")
			.image("image_url")
			.price(1000L)
			.discountPrice(100L)
			.quantity(1)
			.bookStatusId(1L)
			.isPackaged(true)
			.build();

		RedisCartHash redisCartHash = RedisCartHash.of(cartId, cartBookDTO);
		when(redisCartRepository.findById(cartId)).thenReturn(Optional.of(redisCartHash));
		redisCartService.addCartBook(cartId, cartBookDTO);
		verify(redisCartRepository, times(1)).save(any(RedisCartHash.class));
	}

	@Test
	void addCartBook_ExistingBook() {
		Object cartId = "testCartId";
		CartBookDTO existingBookDTO = CartBookDTO.builder()
			.bookId(1L)
			.title("test")
			.image("image_url")
			.price(1000L)
			.discountPrice(100L)
			.quantity(1)
			.bookStatusId(1L)
			.isPackaged(true)
			.build();

		CartBookDTO newBookDTO = CartBookDTO.builder()
			.bookId(1L)
			.title("test")
			.image("image_url")
			.price(1000L)
			.discountPrice(100L)
			.quantity(2)
			.bookStatusId(1L)
			.isPackaged(true)
			.build();

		List<CartBookDTO> cartBookList = new ArrayList<>();
		cartBookList.add(existingBookDTO);

		RedisCartHash redisCartHash = RedisCartHash.of(cartId, new CartBookListDTO(cartBookList));
		when(redisCartRepository.findById(cartId)).thenReturn(Optional.of(redisCartHash));
		redisCartService.addCartBook(cartId, newBookDTO);
		verify(redisCartRepository, times(1)).save(any(RedisCartHash.class));
	}

	@Test
	void addCartBooks() {
		Object cartId = "testCartId";
		List<CartBookDTO> cartBookDTOList = new ArrayList<>();
		cartBookDTOList.add(CartBookDTO.builder()
			.bookId(1L)
			.title("test")
			.image("image_url")
			.price(1000L)
			.discountPrice(100L)
			.quantity(1)
			.bookStatusId(1L)
			.isPackaged(true)
			.build());
		CartBookListDTO cartBookListDTO = new CartBookListDTO(cartBookDTOList);
		RedisCartHash redisCartHash = RedisCartHash.of(cartId, cartBookListDTO);
		when(redisCartRepository.findById(cartId)).thenReturn(Optional.of(redisCartHash));
		redisCartService.addCartBooks(cartId, cartBookListDTO);
		verify(redisCartRepository, times(1)).save(any(RedisCartHash.class));
	}

	@Test
	void deleteCartBook() {
		Object cartId = "testCartId";
		Long bookId = 1L;
		List<CartBookDTO> cartBookList = new ArrayList<>();
		cartBookList.add(CartBookDTO.builder()
			.bookId(bookId)
			.title("test")
			.image("image_url")
			.price(1000L)
			.discountPrice(100L)
			.quantity(1)
			.bookStatusId(1L)
			.isPackaged(true)
			.build());
		RedisCartHash redisCartHash = RedisCartHash.of(cartId, new CartBookListDTO(cartBookList));
		when(redisCartRepository.findById(cartId)).thenReturn(Optional.of(redisCartHash));
		redisCartService.deleteCartBook(cartId, bookId);
		verify(redisCartRepository, times(1)).save(any(RedisCartHash.class));
	}

	@Test
	void deleteCartBooks() {
		Object cartId = "testCartId";
		List<Long> bookIds = List.of(1L);
		List<CartBookDTO> cartBookList = new ArrayList<>();
		cartBookList.add(CartBookDTO.builder()
			.bookId(1L)
			.title("test")
			.image("image_url")
			.price(1000L)
			.discountPrice(100L)
			.quantity(1)
			.bookStatusId(1L)
			.isPackaged(true)
			.build());
		RedisCartHash redisCartHash = RedisCartHash.of(cartId, new CartBookListDTO(cartBookList));
		when(redisCartRepository.findById(cartId)).thenReturn(Optional.of(redisCartHash));
		redisCartService.deleteCartBooks(cartId, bookIds);
		verify(redisCartRepository, times(1)).save(any(RedisCartHash.class));
	}

	@Test
	void deleteCart() {
		Object cartId = "testCartId";
		redisCartService.deleteCart(cartId);
		verify(redisCartRepository, times(1)).deleteById(cartId);
	}

	@Test
	void notExistCart() {
		Object cartId = "testCartId";
		when(redisCartRepository.findById(cartId)).thenReturn(Optional.empty());
		boolean result = redisCartService.notExistCart(cartId);
		assertTrue(result);
	}

	@Test
	void updateCartBookQuantity() {
		Object cartId = "testCartId";
		CartBookDTO cartBookDTO = CartBookDTO.builder()
			.bookId(1L)
			.title("test")
			.image("image_url")
			.price(1000L)
			.discountPrice(100L)
			.quantity(1)
			.bookStatusId(1L)
			.isPackaged(true)
			.build();

		List<CartBookDTO> cartBookList = new ArrayList<>();
		cartBookList.add(cartBookDTO);
		RedisCartHash redisCartHash = RedisCartHash.of(cartId, new CartBookListDTO(cartBookList));
		UpdateCartBookQuantityRequest updateRequest = new UpdateCartBookQuantityRequest(1L, 2);

		when(redisCartRepository.findById(cartId)).thenReturn(Optional.of(redisCartHash));
		redisCartService.updateCartBookQuantity(cartId, updateRequest);
		verify(redisCartRepository, times(1)).save(any(RedisCartHash.class));
	}
}
