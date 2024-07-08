package store.novabook.front.store.cart.repository;

import org.springframework.data.repository.CrudRepository;

import store.novabook.front.store.cart.hash.RedisCartId;

public interface RedisCartIdRepository extends CrudRepository<RedisCartId, Object> {
}
