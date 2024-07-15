package store.novabook.front.store.order.repository;

import org.springframework.data.repository.CrudRepository;

import store.novabook.front.store.order.dto.OrderTemporaryNonMemberForm;

public interface RedisOrderNonMemberRepository extends CrudRepository<OrderTemporaryNonMemberForm, String> {
	boolean existsById(String code);
}
