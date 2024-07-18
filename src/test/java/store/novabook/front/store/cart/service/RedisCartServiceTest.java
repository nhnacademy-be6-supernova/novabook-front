package store.novabook.front.store.cart.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.cart.dto.CartBookDTO;
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
	 void testCreateCart() {
		 // Mocking
		 Object cartId = "testCartId";
		 RedisCartHash expectedCartHash = RedisCartHash.of(cartId);

		 // Mock behavior of repository.save()
		 when(redisCartRepository.save(expectedCartHash)).thenReturn(expectedCartHash);

		 // Call the method under test
		 redisCartService.createCart(cartId);

		 // Verify that repository.save() was called exactly once with expectedCartHash
		 verify(redisCartRepository, times(1)).save(expectedCartHash);
	 }
	@Test
	void testGetCartList() {
		Object cartId = "testCartId";
		RedisCartHash expectedCartHash = RedisCartHash.of(cartId);

		when(redisCartRepository.findById(cartId)).thenReturn(Optional.of(expectedCartHash));

		RedisCartHash result = redisCartService.getCartList(cartId);

		verify(redisCartRepository, times(1)).findById(cartId);
		Assertions.assertEquals(expectedCartHash, result);
	}

	@Test
	void testAddCartBook() {
		Object cartId = "testCartId";
		CartBookDTO cartBookDTO = CartBookDTO.builder()
			.bookId(1L)
			.title("Effective Java")
			.image("https://example.com/effective-java.jpg")
			.price(4500L)
			.discountPrice(4000L)
			.quantity(1)
			.isPackaged(false)
			.bookStatusId(2L)
			.build();

		when(redisCartRepository.findById(cartId)).thenReturn(Optional.empty());

		redisCartService.addCartBook(cartId, cartBookDTO);

		verify(redisCartRepository, times(1)).save(any(RedisCartHash.class));
	}

	@Test
	void testDeleteCartBook() {
		Object cartId = "testCartId";
		Long bookId = 1L;

		CartBookDTO cartBookDTO = CartBookDTO.builder()
			.bookId(1L)
			.title("Effective Java")
			.image("https://example.com/effective-java.jpg")
			.price(4500L)
			.discountPrice(4000L)
			.quantity(1)
			.isPackaged(false)
			.bookStatusId(2L)
			.build();
		List<CartBookDTO> cartBookList = new ArrayList<>();
		cartBookList.add(cartBookDTO);

		RedisCartHash existingCartHash = new RedisCartHash(cartId, cartBookList);

		when(redisCartRepository.findById(cartId)).thenReturn(Optional.of(existingCartHash));

		redisCartService.deleteCartBook(cartId, bookId);

		verify(redisCartRepository, times(1)).save(any(RedisCartHash.class));
	}

	// Add more test methods to cover other scenarios for RedisCartService

}
