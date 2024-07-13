package store.novabook.front.store.order.service;

import java.util.UUID;

import store.novabook.front.store.order.dto.OrderTemporaryFormRequest;

public interface RedisOrderService {
	String createOrderForm(OrderTemporaryFormRequest orderTemporaryFormRequest, String cartCode);
}
