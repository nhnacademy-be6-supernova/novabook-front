package store.novabook.front.store.cart.controller;

import static store.novabook.front.common.util.CookieUtil.*;

import java.util.Objects;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.cart.dto.request.CreateCartBookListRequest;
import store.novabook.front.api.cart.dto.request.CreateCartBookRequest;
import store.novabook.front.api.cart.dto.request.DeleteCartBookListRequest;
import store.novabook.front.api.cart.dto.response.CreateCartBookResponse;
import store.novabook.front.common.security.aop.CurrentMembers;
import store.novabook.front.common.util.CookieUtil;
import store.novabook.front.store.cart.hash.RedisCartHash;
import store.novabook.front.store.cart.service.CartService;
import store.novabook.front.store.cart.service.RedisCartService;

@RestController
@RequestMapping("/api/v1/front/carts")
@RequiredArgsConstructor
public class CartRestController {

	private final CartService cartService;
	private final RedisCartService redisCartService;

	@PostMapping
	public ResponseEntity<CreateCartBookResponse> addCartBook(
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers Long memberId,
		HttpServletResponse response,
		@RequestBody CreateCartBookRequest request) {

		//로그인되어 있을때
		if (Objects.nonNull(memberId) && Objects.isNull(guestCookie)) {
			if (!redisCartService.existsCart(memberId)) {
				redisCartService.creatCart(memberId);
			}
			cartService.addCartBook(request);
			redisCartService.addCartBook(memberId, request);
			return ResponseEntity.ok().build();
		}

		// 비회원이면서 장바구니에 어떤 상품도 담겨있지 않은 상태라면 비회원용 장바구니 UUID를 발급해서 넣어준다.
		if (Objects.isNull(guestCookie)) {
			String uuid = String.valueOf(UUID.randomUUID());
			CookieUtil.createGuestCookie(response, uuid);
			redisCartService.creatCart(uuid);
			redisCartService.addCartBook(uuid, request);
		} else {
			String uuid = guestCookie.getValue();
			redisCartService.addCartBook(uuid, request);
		}

		//로그인 되어있는데 비회원 쿠키가 존재할 경우
		if (Objects.nonNull(memberId) && Objects.nonNull(guestCookie)) {
			RedisCartHash redisCartHash = redisCartService.getCartList(guestCookie.getValue());
			if (!redisCartService.existsCart(memberId)) {
				redisCartService.creatCart(memberId);
			}
			if (Objects.nonNull(redisCartHash.cartBookList())) {
				redisCartHash.cartBookList().add(request);
				cartService.addCartBooks(new CreateCartBookListRequest(redisCartHash.cartBookList()));
				redisCartService.addCartBooks(memberId, new CreateCartBookListRequest(redisCartHash.cartBookList()));
			}

			CookieUtil.deleteGuestCookie(response);
		}

		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteCartBooks(
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers Long memberId,
		HttpServletResponse response,
		@RequestBody DeleteCartBookListRequest request) {

		if (Objects.nonNull(memberId)) {
			redisCartService.deleteCartBooks(memberId, request.bookIds());
			cartService.deleteCartBooks(request);

		} else if (Objects.nonNull(guestCookie)) {
			redisCartService.deleteCartBooks(guestCookie.getValue(), request.bookIds());

		}

		return ResponseEntity.ok().build();
	}

}
