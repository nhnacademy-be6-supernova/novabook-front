package store.novabook.front.store.cart.controller;

import static store.novabook.front.common.util.CookieUtil.*;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookIdDTO;
import store.novabook.front.api.cart.dto.CartBookListDTO;
import store.novabook.front.api.cart.dto.request.DeleteCartBookListRequest;
import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
import store.novabook.front.common.handler.HandleWithAlert;
import store.novabook.front.common.security.aop.CurrentMembers;
import store.novabook.front.common.util.CookieUtil;
import store.novabook.front.store.cart.hash.RedisCartHash;
import store.novabook.front.store.cart.service.CartService;
import store.novabook.front.store.cart.service.RedisCartService;

@HandleWithAlert
@RestController
@RequestMapping("/api/v1/front/carts")
@RequiredArgsConstructor
public class CartRestController {

	private final CartService cartService;
	private final RedisCartService redisCartService;

	@PostMapping
	public ResponseEntity<Object> addCartBook(
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers(required = false) Long memberId, HttpServletResponse response,
		@Valid @RequestBody CartBookDTO request) {

		//로그인되어 있을때
		if (Objects.nonNull(memberId) && Objects.isNull(guestCookie)) {
			if (redisCartService.notExistCart(memberId)) {
				redisCartService.createCart(memberId);
			}
			cartService.addCartBook(request);
			redisCartService.addCartBook(memberId, request);
			return ResponseEntity.ok().build();
		}

		// 비회원이면서 장바구니에 어떤 상품도 담겨있지 않은 상태라면 비회원용 장바구니 UUID를 발급해서 넣어준다.
		if (Objects.isNull(guestCookie)) {
			String uuid = String.valueOf(UUID.randomUUID());
			CookieUtil.createGuestCookie(response, uuid);
			redisCartService.createCart(uuid);
			redisCartService.addCartBook(uuid, request);
		} else {
			String uuid = guestCookie.getValue();
			cartService.addCartBook(request);

			redisCartService.addCartBook(uuid, request);
		}

		//로그인 되어있는데 비회원 쿠키가 존재할 경우
		if (Objects.nonNull(memberId) && Objects.nonNull(guestCookie.getValue())) {
			RedisCartHash redisCartHash = redisCartService.getCartList(guestCookie.getValue());
			if (redisCartService.notExistCart(memberId)) {
				redisCartService.createCart(memberId);
			}
			if (Objects.nonNull(redisCartHash.getCartBookList())) {
				redisCartHash.getCartBookList().add(request);
				cartService.addCartBooks(new CartBookListDTO(redisCartHash.getCartBookList()));
				redisCartService.addCartBooks(memberId, new CartBookListDTO(redisCartHash.getCartBookList()));
			}

			CookieUtil.deleteGuestCookie(response);
		}

		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteCartBooks(
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers(required = false) Long memberId, @Valid @RequestBody DeleteCartBookListRequest request) {

		if (Objects.nonNull(memberId)) {
			redisCartService.deleteCartBooks(memberId, request.bookIds());
			cartService.deleteCartBooks(request);

		} else if (Objects.nonNull(guestCookie)) {
			redisCartService.deleteCartBooks(guestCookie.getValue(), request.bookIds());

		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/refresh")
	public ResponseEntity<Void> refresh(@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers(required = false) Long memberId,
		@Valid @RequestBody(required = false) CartBookIdDTO cartBookIdDTO) {

		//비회원 장바구니 최신정보로 업데이트
		if (Objects.nonNull(guestCookie) && !cartBookIdDTO.bookIdsAndQuantity().isEmpty()) {
			redisCartService.deleteCart(guestCookie.getValue());
			redisCartService.createCart(guestCookie.getValue());
			redisCartService.addCartBooks(guestCookie.getValue(), cartService.getCartListByGuest(cartBookIdDTO));
		}

		//로그인되어 있을때
		if (Objects.nonNull(memberId)) {
			CartBookListDTO getCartResponse = cartService.getCartList();
			redisCartService.deleteCart(memberId);
			redisCartService.createCart(memberId);
			if (!getCartResponse.getCartBookList().isEmpty()) {
				redisCartService.addCartBooks(memberId, new CartBookListDTO(getCartResponse.getCartBookList()));
			}
		}
		return ResponseEntity.ok().build();
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateCart(
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers(required = false) Long memberId, @Valid @RequestBody UpdateCartBookQuantityRequest request) {
		if (Objects.nonNull(memberId)) {
			cartService.updateCartBookQuantity(request);
			redisCartService.updateCartBookQuantity(memberId, request);

		} else if (Objects.nonNull(guestCookie)) {
			redisCartService.updateCartBookQuantity(guestCookie.getValue(), request);

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error", "회원 또는 게스트 정보를 찾을 수 없습니다."));
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/count")
	public ResponseEntity<Object> getCartCount(
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers(required = false) Long memberId) {
		int count = 0;
		if (Objects.nonNull(memberId)) {
			count = redisCartService.getCartCount(memberId);
		} else if (Objects.nonNull(guestCookie)) {
			count = redisCartService.getCartCount(guestCookie.getValue());
		}

		return ResponseEntity.ok().body(count);
	}

}
