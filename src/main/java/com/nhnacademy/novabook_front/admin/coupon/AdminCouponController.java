package com.nhnacademy.novabook_front.admin.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/coupons")
@Controller
public class AdminCouponController {

    @GetMapping
    public String getCoupons() {
        return "admin/coupon/coupon_list";
    }

    @GetMapping("/common/form")
    public String getCouponCommonForm() {
        return "admin/coupon/coupon_common_form";
    }

    @GetMapping("/book/form")
    public String getCouponBookForm() {
        return "admin/coupon/coupon_book_form";
    }

    @GetMapping("/category/form")
    public String getCouponCategoryForm() {
        return "admin/coupon/coupon_category_form";
    }

    @PostMapping("/common/form")
    public String createCouponCommon() {
        return "";
    }

    @PostMapping("/book/form")
    public String createCouponBook() {
        return "";
    }

    @PostMapping("/category/form")
    public String createCouponCategory() {
        return "";
    }

}
