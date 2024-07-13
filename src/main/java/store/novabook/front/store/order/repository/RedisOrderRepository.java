package store.novabook.front.store.order.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import jakarta.validation.constraints.NotNull;
import store.novabook.front.store.order.dto.OrderTemporaryForm;

public interface RedisOrderRepository extends CrudRepository<OrderTemporaryForm, Long> {
	boolean existsById(Long id);
}
