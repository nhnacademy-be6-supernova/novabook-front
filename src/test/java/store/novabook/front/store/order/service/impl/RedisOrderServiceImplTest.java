package store.novabook.front.store.order.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.common.util.UniqueRandomCodeGenerator;
import store.novabook.front.store.book.dto.BookIdAndQuantityDTO;
import store.novabook.front.store.order.dto.OrderAddressInfo;
import store.novabook.front.store.order.dto.OrderReceiverInfo;
import store.novabook.front.store.order.dto.OrderSenderInfo;
import store.novabook.front.store.order.dto.OrderTemporaryFormRequest;
import store.novabook.front.store.order.repository.RedisOrderNonMemberRepository;
import store.novabook.front.store.order.repository.RedisOrderRepository;

class RedisOrderServiceImplTest {

	@InjectMocks
	private RedisOrderServiceImpl redisOrderService;

	@Mock
	private RedisOrderRepository redisOrderRepository;

	@Mock
	private RedisOrderNonMemberRepository redisOrderNonMemberRepository;

	@Mock
	private UniqueRandomCodeGenerator uniqueRandomCodeGenerator;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateOrderFormForMember() {
		String generatedCode = "230719ABCDE";
		when(uniqueRandomCodeGenerator.generateUniqueRandomCode()).thenReturn(generatedCode);

		OrderSenderInfo orderSenderInfo = OrderSenderInfo.builder()
			.name("Sender Name")
			.phone("01012345678")
			.build();

		OrderReceiverInfo orderReceiverInfo = new OrderReceiverInfo(
			new OrderAddressInfo("123 Main St", "Seoul", "04524"),
			"Receiver Name",
			"01012345678",
			"receiver@example.com"
		);
		OrderTemporaryFormRequest request = OrderTemporaryFormRequest.builder()
			.memberId(1L)
			.books(List.of(new BookIdAndQuantityDTO(1L, 2), new BookIdAndQuantityDTO(2L, 3)))
			.wrappingPaperId(1L)
			.couponId(1L)
			.usePointAmount(500)
			.deliveryDate(LocalDate.now().plusDays(1))
			.deliveryId(1L)
			.orderSenderInfo(orderSenderInfo)
			.orderReceiverInfo(orderReceiverInfo)
			.build();

		String code = redisOrderService.createOrderForm(request, "someCartUUID");

		assertEquals(generatedCode, code);

	}

	@Test
	void testCreateOrderFormForNonMember() {
		String generatedCode = "230719XYZ";
		when(uniqueRandomCodeGenerator.generateUniqueRandomCode()).thenReturn(generatedCode);
		OrderSenderInfo orderSenderInfo = OrderSenderInfo.builder()
			.name("Sender Name")
			.phone("01012345678")
			.build();

		OrderReceiverInfo orderReceiverInfo = new OrderReceiverInfo(
			new OrderAddressInfo("123 Main St", "Seoul", "04524"),
			"Receiver Name",
			"01012345678",
			"receiver@example.com"
		);
		OrderTemporaryFormRequest request = OrderTemporaryFormRequest.builder()
			.memberId(1L)
			.books(List.of(new BookIdAndQuantityDTO(1L, 2), new BookIdAndQuantityDTO(2L, 3)))
			.wrappingPaperId(1L)
			.couponId(1L)
			.usePointAmount(500)
			.deliveryDate(LocalDate.now().plusDays(1))
			.deliveryId(1L)
			.orderSenderInfo(orderSenderInfo)
			.orderReceiverInfo(orderReceiverInfo)
			.build();

		String code = redisOrderService.createOrderForm(request, "someCartUUID");

		assertEquals(generatedCode, code);

	}
}
