package com.nhnacademy.viewtest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CouponController {
    @GetMapping("/coupon")
    public String home() {
        return "coupon2";
    }

    @GetMapping("/useCoupon")
    public String usecoupon() {
        return "usedcoupon";}

}
