package store.novabook.front.store.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookListDTO;
import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
import store.novabook.front.store.cart.hash.RedisCartHash;
import store.novabook.front.store.cart.repository.RedisCartRepository;

@Service
@RequiredArgsConstructor
public class RedisCartService {

	private final RedisCartRepository redisCartRepository;
	private final RedisTemplate<String, Object> redisCartTemplate;

	public void createCart(Object cartId) {
		RedisCartHash newCart = RedisCartHash.of(cartId);

		redisCartRepository.save(newCart);
		redisCartTemplate.expire(newCart.getCartId().toString(), 10, TimeUnit.SECONDS);
	}

	public RedisCartHash getCartList(Object cartId) {
		Optional<RedisCartHash> redisCartHash = redisCartRepository.findById(cartId);
		if (redisCartHash.isPresent() && Objects.nonNull(redisCartHash.get().getCartBookList())) {
			return redisCartHash.get();
		}
		return RedisCartHash.of(cartId);
	}

	public void addCartBook(Object cartId, CartBookDTO request) {
		Optional<RedisCartHash> redisCartHashOpt = redisCartRepository.findById(cartId);
		if (redisCartHashOpt.isPresent() && Objects.nonNull(redisCartHashOpt.get().getCartBookList())) {
			RedisCartHash redisCartHash = redisCartHashOpt.get();
			List<CartBookDTO> updatedCartBookList = redisCartHash.getCartBookList();

			boolean bookExists = false;
			for (CartBookDTO cartBook : updatedCartBookList) {
				if (cartBook.bookId().equals(request.bookId())) {
					// 동일한 도서가 이미 존재하면 수량 증가
					int newQuantity = cartBook.quantity() + request.quantity();
					updatedCartBookList.remove(cartBook); // 기존 도서 항목 삭제
					updatedCartBookList.add(
						CartBookDTO.update(cartBook.bookId(), newQuantity, request)); // 새로운 도서 항목 추가
					bookExists = true;
					break;
				}
			}

			// 동일한 도서가 존재하지 않으면 새로 추가
			if (!bookExists) {
				updatedCartBookList.add(request);
			}

			redisCartRepository.save(new RedisCartHash(cartId, updatedCartBookList));

		} else {
			RedisCartHash newCart = RedisCartHash.of(cartId, request);
			redisCartRepository.save(newCart);
		}
	}

	public void addCartBooks(Object cartId, CartBookListDTO request) {
		Optional<RedisCartHash> redisCartHashOpt = redisCartRepository.findById(cartId);
		if (redisCartHashOpt.isPresent() && Objects.nonNull(redisCartHashOpt.get().getCartBookList())) {
			RedisCartHash redisCartHash = redisCartHashOpt.get();
			List<CartBookDTO> updatedCartBookList = new ArrayList<>(redisCartHash.getCartBookList());
			updatedCartBookList.addAll(request.getCartBookList());
			RedisCartHash updatedRedisCartHash = RedisCartHash.builder()
				.cartId(cartId)
				.cartBookList(updatedCartBookList)
				.build();
			redisCartRepository.save(updatedRedisCartHash);
		} else {
			RedisCartHash newCart = RedisCartHash.of(cartId, request);
			redisCartRepository.save(newCart);
		}
	}

	public void deleteCartBook(Object cartId, Long bookId) {
		Optional<RedisCartHash> redisCartHashOpt = redisCartRepository.findById(cartId);
		if (redisCartHashOpt.isPresent() && Objects.nonNull(redisCartHashOpt.get().getCartBookList())) {
			RedisCartHash redisCartHash = redisCartHashOpt.get();
			List<CartBookDTO> updatedCartBookList = redisCartHash.getCartBookList().stream()
				.filter(cartBook -> !cartBook.bookId().equals(bookId))
				.toList();
			RedisCartHash updatedRedisCartHash = new RedisCartHash(cartId, updatedCartBookList);
			redisCartRepository.save(updatedRedisCartHash);
		}
	}

	public void deleteCartBooks(Object cartId, List<Long> bookIds) {
		Optional<RedisCartHash> redisCartHashOpt = redisCartRepository.findById(cartId);
		if (redisCartHashOpt.isPresent() && Objects.nonNull(redisCartHashOpt.get().getCartBookList())) {
			RedisCartHash redisCartHash = redisCartHashOpt.get();
			List<CartBookDTO> updatedCartBookList = redisCartHash.getCartBookList().stream()
				.filter(cartBook -> !bookIds.contains(cartBook.bookId()))
				.toList();
			RedisCartHash updatedRedisCartHash = new RedisCartHash(cartId, updatedCartBookList);
			redisCartRepository.save(updatedRedisCartHash);
		}
	}

	public void deleteCart(Object cartId) {
		redisCartRepository.deleteById(cartId);
	}

	public boolean notExistCart(Object cartId) {
		Optional<RedisCartHash> redisCartHashOpt = redisCartRepository.findById(cartId);
		return redisCartHashOpt.isEmpty();
	}

	public void updateCartBookQuantity(Object cartId, UpdateCartBookQuantityRequest request) {
		Optional<RedisCartHash> redisCartHashOpt = redisCartRepository.findById(cartId);
		if (redisCartHashOpt.isPresent() && Objects.nonNull(redisCartHashOpt.get().getCartBookList())) {
			RedisCartHash redisCartHash = redisCartHashOpt.get();
			List<CartBookDTO> updatedCartBookList = redisCartHash.getCartBookList();
			for (CartBookDTO cartBook : updatedCartBookList) {
				if (cartBook.bookId().equals(request.bookId())) {

					CartBookDTO updatedCartBook = CartBookDTO.update(request.bookId(), request.quantity(), cartBook);

					int index = updatedCartBookList.indexOf(cartBook);
					updatedCartBookList.set(index, updatedCartBook);
					break;
				}
			}

			redisCartHash.update(updatedCartBookList);
			redisCartRepository.save(redisCartHash);
		}
	}

	public int getCartCount(Object cartId) {
		int count = 0;
		Optional<RedisCartHash> redisCartHashOpt = redisCartRepository.findById(cartId);

		if (redisCartHashOpt.isPresent() && Objects.nonNull(redisCartHashOpt.get().getCartBookList())) {
			count = redisCartHashOpt.get().getCartBookList().size();
		}
		return count;
	}
}
