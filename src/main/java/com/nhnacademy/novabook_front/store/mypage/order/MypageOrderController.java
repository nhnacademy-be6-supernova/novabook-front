package com.nhnacademy.novabook_front.store.mypage.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/mypage/orders")
@Controller
public class MypageOrderController {

    @GetMapping
    public String getOrderAll() {
        return "/store/mypage/order/order_list";
    }

    @GetMapping("/cancel")
    public String getOrderCancelAll() {
        return "/store/mypage/order/order_cancel_list";
    }

}
