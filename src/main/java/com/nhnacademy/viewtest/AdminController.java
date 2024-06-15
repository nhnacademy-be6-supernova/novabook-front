package com.nhnacademy.viewtest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @GetMapping("/admin/coupon")
    public String getCoupon() {
        return "admin_coupon_type";
    }

    @PostMapping("/admin/coupon")
    public String postCoupon(@RequestParam("couponTypeValue") String couponTypeValue,
                             @RequestParam("bookSearchValue") String bookSearchValue,
                             @RequestParam("subCategoryValue") String subCategoryValue,
                             Model model) {

        model.addAttribute("couponTypeValue", couponTypeValue);
        return "admin_coupon_detail";
    }

    @GetMapping("/admin/point")
    public String getPoint() {
        return "admin_point";
    }

    @PostMapping("/admin/point")
    public String postPoint(@RequestParam("basicPoint") String basicPoint,
                            @RequestParam("registerPoint") String registerPoint,
                            @RequestParam("reviewPoint") String reviewPoint) {
        return "/";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin/detail")
    public String adminDetail() {
        return "adminTemplate";
    }

}
