package store.novabook.front.store.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/coupons")
@Controller
public class CouponController {

    @GetMapping
    public String getCouponAll() {
        return "store/coupon/coupon_list";
    }
}
