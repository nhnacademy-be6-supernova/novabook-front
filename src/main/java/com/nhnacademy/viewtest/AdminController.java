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
        return "admin-point";
    }

    @PostMapping("/admin/point")
    public String postPoint(@RequestParam("basicPoint") String basicPoint,
                            @RequestParam("registerPoint") String registerPoint,
                            @RequestParam("reviewPoint") String reviewPoint) {
        return "/";
    }

    @GetMapping("/admin/coupon/general")
    public String getCouponGeneral() {
        return "admin-coupon-general";
    }

    @GetMapping("/admin/coupon/book")
    public String getCouponBook() {
        return "admin-coupon-book";
    }

    @GetMapping("/admin/coupon/category")
    public String getCouponCategory() {
        return "admin-coupon-category";
    }

    @GetMapping("/admin/delivery")
    public String getDelivery() {
        return "admin-delivery";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin/detail")
    public String adminDetail() {
        return "adminTemplate";
    }

    @GetMapping("/admin/coupon/detail")
    public String adminCouponDetail() {
        return "admin-coupon-detail";
    }

    @GetMapping("/admin/order")
    public String adminOrder() {
        return "admin-order";
    }


}
