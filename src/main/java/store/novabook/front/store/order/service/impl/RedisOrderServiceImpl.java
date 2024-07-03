package store.novabook.front.store.order.service.impl;

import org.springframework.stereotype.Service;

import store.novabook.front.store.order.dto.OrderTemporaryForm;
import store.novabook.front.store.order.repository.RedisOrderRepository;
import store.novabook.front.store.order.service.RedisOrderService;

@Service
public class RedisOrderServiceImpl implements RedisOrderService {
	private final RedisOrderRepository repository;

	public RedisOrderServiceImpl(RedisOrderRepository repository) {
		this.repository = repository;
	}
	@Override
	public void create(OrderTemporaryForm orderTemporaryForm) {
		repository.save(orderTemporaryForm);
	}
}
