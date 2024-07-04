package store.novabook.front.store.cart.repository;

import org.springframework.data.repository.CrudRepository;

import store.novabook.front.store.cart.dto.RedisCartStorage;

public interface RedisCartRepository extends CrudRepository<RedisCartStorage, Long> {
}
