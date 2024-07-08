package store.novabook.front.store.cart.repository;

import org.springframework.data.repository.CrudRepository;

import store.novabook.front.store.cart.hash.RedisCartHash;

public interface RedisCartRepository extends CrudRepository<RedisCartHash, Object> {
}
