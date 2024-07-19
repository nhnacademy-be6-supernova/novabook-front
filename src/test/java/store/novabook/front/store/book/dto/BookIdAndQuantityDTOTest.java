package store.novabook.front.store.book.dto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BookIdAndQuantityDTOTest {

	@Test
	void testBookIdAndQuantityDTO() {
		Long id = 1L;
		long quantity = 10;

		BookIdAndQuantityDTO dto = new BookIdAndQuantityDTO(id, quantity);

		assertEquals(id, dto.id());
		assertEquals(quantity, dto.quantity());
	}

	@Test
	void testBookIdAndQuantityDTOWithInvalidQuantity() {
		Long id = 1L;
		long quantity = -1;

		BookIdAndQuantityDTO dto = new BookIdAndQuantityDTO(id, quantity);

		assertEquals(id, dto.id());
		assertEquals(quantity, dto.quantity());
	}
}
