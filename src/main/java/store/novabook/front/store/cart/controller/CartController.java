package store.novabook.front.store.cart.controller;

import static store.novabook.front.common.util.CookieUtil.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookInfoDto;
import store.novabook.front.api.cart.dto.CartBookListDTO;
import store.novabook.front.api.cart.dto.request.GetBookInfoRequest;
import store.novabook.front.api.cart.dto.response.GetBookInfoResponse;
import store.novabook.front.common.exception.NovaException;
import store.novabook.front.common.security.aop.CurrentMembers;
import store.novabook.front.common.util.CookieUtil;
import store.novabook.front.store.cart.hash.RedisCartHash;
import store.novabook.front.store.cart.service.CartService;
import store.novabook.front.store.cart.service.RedisCartService;

@RequestMapping("/carts")
@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {
    public static final String STORE_CART_CART_LIST = "store/cart/cart_list";
    private final CartService cartService;

    //장바구니 버튼 클릭했을 때
    @GetMapping
    public String getCartBookAll(@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
                                 @CurrentMembers(required = false) Long memberId, HttpServletResponse response, Model model) {

        // 비회원 첫방문
        if (Objects.isNull(guestCookie) && (Objects.isNull(memberId))) {
            String uuid = String.valueOf(UUID.randomUUID());
            CookieUtil.createGuestCookie(response, uuid);
            model.addAttribute("cart", Collections.EMPTY_LIST);
            return STORE_CART_CART_LIST;
        }

        // 로그인 되어있는데 비회원 쿠키가 존재할 경우
        if (Objects.nonNull(memberId) && Objects.nonNull(guestCookie)) {
            //장바구니 이관
            cartService.transferGuestCartToMemberCart(guestCookie.getValue(), memberId);
            CookieUtil.deleteGuestCookie(response);
        }

        if (Objects.nonNull(memberId)) {
            model.addAttribute("cart", cartService.getCartBookAll(memberId));
        } else {
            model.addAttribute("cart", cartService.getCartBookAll(guestCookie.getValue()));
        }

        return STORE_CART_CART_LIST;
    }


    @GetMapping("/delete/{bookId}")
    public String deleteCart(@PathVariable Long bookId,
                             @CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie,
                             @CurrentMembers(required = false) Long memberId) {
        if (Objects.nonNull(memberId)) {

            try {
                cartService.deleteCartBook(memberId, bookId);
            } catch (NovaException e) {
                log.error(e.getMessage(), e);
            }

        } else if (Objects.nonNull(guestCookie)) {
            cartService.deleteCartBook(guestCookie.getValue(), bookId);

        }

        return "redirect:/carts";
    }

}
