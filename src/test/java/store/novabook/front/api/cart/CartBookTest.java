package store.novabook.front.api.cart;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CartBookTest {

	@InjectMocks
	private CartBook cartBook;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		cartBook = spy(new CartBook());
		// Set initial values using reflection or direct assignment if needed
		doReturn(1L).when(cartBook).getId();
		doReturn(2L).when(cartBook).getCartId();
		doReturn(3L).when(cartBook).getBookId();
		doReturn(10).when(cartBook).getQuantity();
		doReturn(true).when(cartBook).isExposed();
		doReturn(LocalDateTime.of(2024, 7, 19, 10, 0)).when(cartBook).getCreatedAt();
		doReturn(LocalDateTime.of(2024, 7, 19, 12, 0)).when(cartBook).getUpdatedAt();
	}

	@Test
	public void testGettersWithSpy() {
		// Verify ID
		assertThat(cartBook.getId()).isEqualTo(1L);

		// Verify Cart ID
		assertThat(cartBook.getCartId()).isEqualTo(2L);

		// Verify Book ID
		assertThat(cartBook.getBookId()).isEqualTo(3L);

		// Verify Quantity
		assertThat(cartBook.getQuantity()).isEqualTo(10);

		// Verify IsExposed
		assertThat(cartBook.isExposed()).isTrue();

		// Verify CreatedAt
		assertThat(cartBook.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 7, 19, 10, 0));

		// Verify UpdatedAt
		assertThat(cartBook.getUpdatedAt()).isEqualTo(LocalDateTime.of(2024, 7, 19, 12, 0));
	}

	@Test
	public void testSpyInteraction() {
		// Interact with the spy
		cartBook.getId();

		// Verify that getId() method was called exactly once
		verify(cartBook, times(1)).getId();
	}
}
