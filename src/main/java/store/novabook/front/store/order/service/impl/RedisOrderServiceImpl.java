package store.novabook.front.store.order.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.store.order.dto.OrderTemporaryForm;
import store.novabook.front.store.order.dto.OrderTemporaryFormRequest;
import store.novabook.front.store.order.dto.OrderTemporaryNonMemberForm;
import store.novabook.front.store.order.repository.RedisOrderNonMemberRepository;
import store.novabook.front.store.order.repository.RedisOrderRepository;
import store.novabook.front.store.order.service.RedisOrderService;

@RequiredArgsConstructor
@Service
public class RedisOrderServiceImpl implements RedisOrderService {

	private final RedisOrderRepository redisOrderRepository;
	private final RedisOrderNonMemberRepository redisOrderNonMemberRepository;

	/**
	 * 비회원일 경우, orderUUID로 ID 저장
	 * 회원일 경우, memberID로 ID 저장 -> 나갔다가 와도 주문 정보 불러올 수 있음
	 * @param orderTemporaryFormRequest
	 * @return
	 */
	@Override
	public UUID createOrderForm(OrderTemporaryFormRequest orderTemporaryFormRequest) {
		UUID orderUUID = UUID.randomUUID();
		// 비회원 가주문 폼 저장
		if (orderTemporaryFormRequest.memberId() == null) {
			OrderTemporaryNonMemberForm orderTemporaryForm = OrderTemporaryNonMemberForm.builder()
				.orderUUID(orderUUID)
				.books(orderTemporaryFormRequest.books())
				.wrappingPaperId(orderTemporaryFormRequest.wrappingPaperId())
				.couponId(orderTemporaryFormRequest.couponId())
				.usePointAmount(orderTemporaryFormRequest.usePointAmount())
				.deliveryDate(orderTemporaryFormRequest.deliveryDate())
				.deliveryId(orderTemporaryFormRequest.deliveryId())
				.orderSenderInfo(orderTemporaryFormRequest.orderSenderInfo())
				.orderReceiverInfo(orderTemporaryFormRequest.orderReceiverInfo())
				.build();
			redisOrderNonMemberRepository.save(orderTemporaryForm);
		} else {
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
			redisOrderRepository.save(orderTemporaryForm);
		}
		return orderUUID;
	}
}
