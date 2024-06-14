package com.nhnacademy.viewtest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MyPageController {
    @GetMapping("/mypage")
    public String home() {

        return "mypage";
    }
    @GetMapping("/mypage/point")
    public String point() {

        return "point-details";
    }
    @GetMapping("/mypage/coupon")
    public String coupon() {

        return "coupon-details";
    }
    @GetMapping("/mypage/orders")
    public String orderlist() {

        return "orderlist-details";
    }
    @GetMapping("/mypage/review")
    public String review() {

        return "review";
    }
    @GetMapping("/mypage/address")
    public String address() {

        return "address";
    }

}
