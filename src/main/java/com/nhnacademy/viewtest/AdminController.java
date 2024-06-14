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
    public String postCoupon(@RequestParam("selectedItem") String selectedItem,
                             @RequestParam("adminSubmittedValue") String adminSubmittedValue,
                             @RequestParam("selectedAdditionalItem") String selectedAdditionalItem,
                             Model model) {

        model.addAttribute("adminSubmittedValue", adminSubmittedValue);
        return "admin_coupon_detail";
    }
}
