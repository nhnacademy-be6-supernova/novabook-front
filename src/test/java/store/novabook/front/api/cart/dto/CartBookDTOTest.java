package store.novabook.front.api.cart.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CartBookDTOTest {

	@Test
	void testCartBookDTOBuilder() {
		Long bookId = 1L;
		String title = "Sample Book Title";
		String image = "http://example.com/image.jpg";
		Long price = 1000L;
		Long discountPrice = 800L;
		Integer quantity = 2;
		boolean isPackaged = true;
		Long bookStatusId = 10L;

		CartBookDTO cartBookDTO = CartBookDTO.builder()
			.bookId(bookId)
			.title(title)
			.image(image)
			.price(price)
			.discountPrice(discountPrice)
			.quantity(quantity)
			.isPackaged(isPackaged)
			.bookStatusId(bookStatusId)
			.build();

		assertThat(cartBookDTO).isNotNull();
		assertThat(cartBookDTO.bookId()).isEqualTo(bookId);
		assertThat(cartBookDTO.title()).isEqualTo(title);
		assertThat(cartBookDTO.image()).isEqualTo(image);
		assertThat(cartBookDTO.price()).isEqualTo(price);
		assertThat(cartBookDTO.discountPrice()).isEqualTo(discountPrice);
		assertThat(cartBookDTO.quantity()).isEqualTo(quantity);
		assertThat(cartBookDTO.isPackaged()).isEqualTo(isPackaged);
		assertThat(cartBookDTO.bookStatusId()).isEqualTo(bookStatusId);
	}

	@Test
	void testUpdateMethod() {
		Long originalBookId = 1L;
		Integer newQuantity = 3;
		CartBookDTO originalDTO = CartBookDTO.builder()
			.bookId(originalBookId)
			.title("Sample Book Title")
			.image("http://example.com/image.jpg")
			.price(1000L)
			.discountPrice(800L)
			.quantity(2)
			.isPackaged(true)
			.bookStatusId(10L)
			.build();

		CartBookDTO updatedDTO = CartBookDTO.update(2L, newQuantity, originalDTO);

		assertThat(updatedDTO).isNotNull();
		assertThat(updatedDTO.bookId()).isEqualTo(2L);
		assertThat(updatedDTO.title()).isEqualTo(originalDTO.title());
		assertThat(updatedDTO.image()).isEqualTo(originalDTO.image());
		assertThat(updatedDTO.price()).isEqualTo(originalDTO.price());
		assertThat(updatedDTO.discountPrice()).isEqualTo(originalDTO.discountPrice());
		assertThat(updatedDTO.quantity()).isEqualTo(newQuantity);
		assertThat(updatedDTO.isPackaged()).isEqualTo(originalDTO.isPackaged());
		assertThat(updatedDTO.bookStatusId()).isEqualTo(originalDTO.bookStatusId());
	}
}
