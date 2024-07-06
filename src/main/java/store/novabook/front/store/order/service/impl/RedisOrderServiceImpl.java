package store.novabook.front.store.order.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import store.novabook.front.store.order.dto.OrderTemporaryForm;
import store.novabook.front.store.order.dto.OrderTemporaryFormRequest;
import store.novabook.front.store.order.repository.RedisOrderRepository;
import store.novabook.front.store.order.service.RedisOrderService;

@Service
public class RedisOrderServiceImpl implements RedisOrderService {

	private final RedisOrderRepository repository;

	public RedisOrderServiceImpl(RedisOrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public UUID createOrderForm(OrderTemporaryFormRequest orderTemporaryFormRequest) {
		UUID orderUUID = UUID.randomUUID();

		OrderTemporaryForm orderTemporaryForm = OrderTemporaryForm.builder()
			.orderUUID(orderUUID)
			.memberId(orderTemporaryFormRequest.memberId())
			.books(orderTemporaryFormRequest.books())
			.wrappingPaperId(orderTemporaryFormRequest.wrappingPaperId())
			.couponId(orderTemporaryFormRequest.couponId())
			.usePointAmount(orderTemporaryFormRequest.usePointAmount())
			.deliveryDate(orderTemporaryFormRequest.deliveryDate())
			.deliveryId(orderTemporaryFormRequest.deliveryId())
			.orderSenderInfo(orderTemporaryFormRequest.orderSenderInfo())
			.orderReceiverInfo(orderTemporaryFormRequest.orderReceiverInfo())
			.build();

		repository.save(orderTemporaryForm);
		return orderUUID;
	}
}
