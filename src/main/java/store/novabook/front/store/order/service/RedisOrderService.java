package store.novabook.front.store.order.service;

import store.novabook.front.store.order.dto.OrderTemporaryForm;

public interface RedisOrderService {
	void create(OrderTemporaryForm orderTemporaryForm);
}
