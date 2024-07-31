package store.novabook.front.store.order.controller;

import static store.novabook.front.common.util.CookieUtil.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.api.coupon.client.CouponClient;
import store.novabook.front.api.coupon.dto.request.GetCouponAllRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.member.coupon.service.MemberCouponClient;
import store.novabook.front.store.order.dto.OrderTemporaryFormRequest;
import store.novabook.front.store.order.service.RedisOrderService;
import store.novabook.front.store.order.service.impl.RedisOrderServiceImpl;

@RequestMapping("/orders")
@RestController
public class OrderRestController {

	private final RedisOrderService orderService;

	private final MemberCouponClient memberCouponClient;
	private final CategoryClient categoryClient;
	private final CouponClient couponClient;

	public OrderRestController(RedisOrderServiceImpl orderService, MemberCouponClient memberCouponClient,
		CategoryClient categoryClient, CouponClient couponClient) {
		this.orderService = orderService;
		this.memberCouponClient = memberCouponClient;
		this.categoryClient = categoryClient;
		this.couponClient = couponClient;
	}

	@PostMapping("/order/form/submit")
	public ResponseEntity<String> createOrderForm(
		@Valid @RequestBody OrderTemporaryFormRequest orderTemporaryFormRequest,
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie) {
		String guestCookieValue = (guestCookie != null) ? guestCookie.getValue() : null;
		String orderCode = orderService.createOrderForm(orderTemporaryFormRequest, guestCookieValue);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderCode);
	}

	@PostMapping("/coupons/sufficient")
	public ResponseEntity<GetCouponAllResponse> getSufficientCoupons(@RequestBody List<Long> bookIds) {
		Set<Long> bookIdList = new HashSet<>(bookIds);
		Set<Long> categoryIdList = new HashSet<>();
		bookIds.forEach(bookId -> {
			if (categoryClient.getCategoryByBId(bookId).getBody() == null) {
				throw new NullPointerException("CategoryResponse body is null for bookId: " + bookId);
			}
			List<Long> categoryIds = categoryClient.getCategoryByBId(bookId).getBody().categoryIds();
			categoryIdList.addAll(categoryIds);
		});
		List<Long> couponIdList = memberCouponClient.getMemberCoupon().getBody().couponIds();

		GetCouponAllRequest couponRequest = GetCouponAllRequest.builder()
			.couponIdList(couponIdList)
			.categoryIdList(categoryIdList)
			.bookIdList(bookIdList)
			.build();
		GetCouponAllResponse response = couponClient.getSufficientCouponAll(couponRequest).getBody();
		return ResponseEntity.ok().body(response);
	}
}
