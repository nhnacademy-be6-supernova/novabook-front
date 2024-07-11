package store.novabook.front.store.order.service;

import java.util.UUID;

import store.novabook.front.store.order.dto.OrderTemporaryFormRequest;

public interface RedisOrderService {
	UUID createOrderForm(OrderTemporaryFormRequest orderTemporaryFormRequest, String cartUUID);
}
