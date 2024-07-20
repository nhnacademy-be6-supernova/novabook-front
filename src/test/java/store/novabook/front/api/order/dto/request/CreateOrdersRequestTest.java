package store.novabook.front.api.order.dto.request;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


class CreateOrdersRequestTest {


	public CreateOrdersRequestTest() {
	}

	@Test
	void testCreateOrdersRequestValid() {
		Long userId = 1L;
		Long deliveryFeeId = 1L;
		Long wrappingPaperId = 1L;
		Long ordersStatusId = 1L;
		LocalDateTime ordersDate = LocalDateTime.now();
		Long totalAmount = 1000L;
		LocalDateTime deliveryDate = LocalDateTime.now().plusDays(1);
		Long bookPurchaseAmount = 5L;
		String deliveryAddress = "123 Main St";
		String receiverName = "John Doe";
		String receiverNumber = "1234567890";

		CreateOrdersRequest request = new CreateOrdersRequest(
			userId, deliveryFeeId, wrappingPaperId, ordersStatusId, ordersDate, totalAmount,
			deliveryDate, bookPurchaseAmount, deliveryAddress, receiverName, receiverNumber
		);

		assertThat(request).isNotNull();
		assertThat(request.userId()).isEqualTo(userId);
		assertThat(request.deliveryFeeId()).isEqualTo(deliveryFeeId);
		assertThat(request.wrappingPaperId()).isEqualTo(wrappingPaperId);
		assertThat(request.ordersStatusId()).isEqualTo(ordersStatusId);
		assertThat(request.ordersDate()).isEqualTo(ordersDate);
		assertThat(request.totalAmount()).isEqualTo(totalAmount);
		assertThat(request.deliveryDate()).isEqualTo(deliveryDate);
		assertThat(request.bookPurchaseAmount()).isEqualTo(bookPurchaseAmount);
		assertThat(request.deliveryAddress()).isEqualTo(deliveryAddress);
		assertThat(request.receiverName()).isEqualTo(receiverName);
		assertThat(request.receiverNumber()).isEqualTo(receiverNumber);
	}

}
