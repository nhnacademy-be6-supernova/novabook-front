package com.nhnacademy.novabook_front.store.mypage.refund;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mypage/refunds")
@Controller
public class MypageRefundController {

    @GetMapping
    public String getRefundAll() {
        return "/store/mypage/refund/refund_list";
    }

}
