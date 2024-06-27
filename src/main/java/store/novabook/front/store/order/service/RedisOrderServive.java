package store.novabook.front.store.order.service;

import org.springframework.stereotype.Service;

import store.novabook.front.store.order.dto.OrderTemporaryForm;
import store.novabook.front.store.order.repository.RedisOrderRepository;

@Service
public class RedisOrderServive {
	private final RedisOrderRepository repository;
	public RedisOrderServive(RedisOrderRepository repository) {
		this.repository = repository;
	}
	public void create(OrderTemporaryForm orderTemporaryForm) {
		repository.save(orderTemporaryForm);
	}
}
