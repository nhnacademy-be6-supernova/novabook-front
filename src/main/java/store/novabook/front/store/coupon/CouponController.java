package store.novabook.front.store.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.service.CouponService;

@RequestMapping("/coupons")
@Controller
@RequiredArgsConstructor
public class CouponController {

	private static final String DEFAULT_PAGE_SIZE = "5";
	public static final String DEFAULT_PAGE = "0";
	private final CouponService couponService;

	@GetMapping
	public String getCouponAll(@RequestParam(defaultValue = DEFAULT_PAGE) int generalPage,
		@RequestParam(defaultValue = DEFAULT_PAGE) int categoryPage,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size, Model model) {
		model.addAttribute("generalCouponList",
			couponService.getCouponTemplateAll(CouponType.GENERAL, true, generalPage, size));
		model.addAttribute("categoryCouponList", couponService.getCategoryCouponTemplateAll(true, categoryPage, size));
		return "store/coupon/coupon_book";
	}

	@GetMapping("/limited")
	public String getLimitedCouponAll(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size, Model model) {
		model.addAttribute("limitedCouponList", couponService.getLimitedCouponTemplateAll(true, page, size));
		return "store/coupon/limited_coupon_list";
	}

}
