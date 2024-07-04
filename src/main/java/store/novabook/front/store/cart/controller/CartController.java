package store.novabook.front.store.cart.controller;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.cart.dto.response.CartIdResponse;
import store.novabook.front.api.cart.dto.response.GetCartResponse;
import store.novabook.front.store.cart.service.CartService;

@RequestMapping("/carts")
@Controller
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;

	//장바구니 버튼 클릭했을 때
	@GetMapping
	public String getCartBookAll(
		@CookieValue(name = "cart", required = false) Cookie cartCookie,
		@CookieValue(name = "Authorization", required = false) Cookie member,
		HttpServletResponse response,
		Model model) {

		if (Objects.nonNull(member)) {
			if (Objects.nonNull(cartCookie)) {
				GetCartResponse getCartResponse = cartService.getCartList(Long.valueOf(cartCookie.getValue()));
				model.addAttribute("cart", getCartResponse);
			} else {
				GetCartResponse getCartResponse = cartService.getCartListByMemberId();
				Cookie cookie = new Cookie("cart", getCartResponse.cartId().toString());
				response.addCookie(cookie);
				model.addAttribute("cart", getCartResponse);
			}

		}

		// if (Objects.nonNull(cartCookie)) {
		// 	String uuid = String.valueOf(UUID.randomUUID());
		// 	Cookie cookie = new Cookie("cart", uuid);
		// 	response.addCookie(cookie);
		// 	model.addAttribute("cart", null);
		// }
		return "store/cart/cart_list";
	}
}
