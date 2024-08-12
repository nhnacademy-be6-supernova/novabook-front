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

	@PostMapping
	public ResponseEntity<Object> addCartBook(
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers(required = false) Long memberId, HttpServletResponse response,
		@Valid @RequestBody CartBookDTO request) {

		// 비회원 첫방문
		if (Objects.isNull(guestCookie) && (Objects.isNull(memberId))) {
			String uuid = String.valueOf(UUID.randomUUID());
			CookieUtil.createGuestCookie(response, uuid);
			cartService.addCartBook(uuid,request);
			return ResponseEntity.ok().build();
		}


		//로그인 되어있는데 비회원 쿠키가 존재할 경우
		if (Objects.nonNull(memberId) && Objects.nonNull(guestCookie)) {
			cartService.transferGuestCartToMemberCart(guestCookie.getValue(), memberId);
			CookieUtil.deleteGuestCookie(response);
			cartService.addCartBook(memberId, request);

		}

		if (Objects.nonNull(memberId)) {
			cartService.addCartBook(memberId, request);
		} else {
			cartService.addCartBook(guestCookie.getValue(), request);
		}

		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteCartBooks(
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers(required = false) Long memberId, @Valid @RequestBody DeleteCartBookListRequest request) {

		if (Objects.nonNull(memberId)) {
			cartService.deleteCartBooks(memberId, request);
		} else if (Objects.nonNull(guestCookie)) {
			cartService.deleteCartBooks(guestCookie.getValue(), request);

		}

		return ResponseEntity.ok().build();
	}

	@PutMapping("/update")
	public ResponseEntity<Object> updateCart(
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers(required = false) Long memberId, @Valid @RequestBody UpdateCartBookQuantityRequest request) {
		if (Objects.nonNull(memberId)) {
			cartService.updateCartBookQuantity(memberId, request);

		} else if (Objects.nonNull(guestCookie)) {
			cartService.updateCartBookQuantity(guestCookie.getValue(), request);

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
			count = cartService.getCartCount(memberId);
		} else if (Objects.nonNull(guestCookie)) {
			count = cartService.getCartCount(guestCookie.getValue());
		}

		return ResponseEntity.ok().body(count);
	}

}
