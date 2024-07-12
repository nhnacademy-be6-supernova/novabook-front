package store.novabook.front.store.order.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import jakarta.validation.constraints.NotNull;
import store.novabook.front.store.order.dto.OrderTemporaryForm;
import store.novabook.front.store.order.dto.OrderTemporaryNonMemberForm;

public interface RedisOrderNonMemberRepository extends CrudRepository<OrderTemporaryNonMemberForm, UUID> {
	boolean existsById(UUID uuid);
}
