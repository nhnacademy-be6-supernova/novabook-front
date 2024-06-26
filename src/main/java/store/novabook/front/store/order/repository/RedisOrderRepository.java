package store.novabook.front.store.order.repository;

import org.springframework.data.repository.CrudRepository;

import store.novabook.front.store.order.dto.OrderTemporaryForm;

public interface RedisOrderRepository extends CrudRepository<OrderTemporaryForm, Long> {
}
