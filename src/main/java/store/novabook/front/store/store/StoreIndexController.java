package store.novabook.front.store.store;

import static store.novabook.front.common.util.CookieUtil.*;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import store.novabook.front.api.book.dto.response.GetBookToMainResponseMap;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.common.security.aop.CurrentMembers;
import store.novabook.front.store.cart.service.RedisCartService;

@Controller
@AllArgsConstructor
public class StoreIndexController {
	private final BookService bookService;
	private final RedisCartService redisCartService;

	@GetMapping
	public String index(Model model, @CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
		@CurrentMembers(required = false) Long memberId) {
		GetBookToMainResponseMap responseMap = bookService.getBookToMainPage();
		model.addAttribute("newBooks", responseMap.mainBookData().get("newBooks"));
		model.addAttribute("popularBooks", responseMap.mainBookData().get("popularBooks"));

		if (Objects.nonNull(memberId)) {
			model.addAttribute("cartCount", redisCartService.getCartCount(memberId));
		} else if (Objects.nonNull(guestCookie)) {
			model.addAttribute("cartCount", redisCartService.getCartCount(guestCookie.getValue()));
		}

		return "store/store_index";
	}
}