package store.novabook.front.store.coupon;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateAllResponse;
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

	@ResponseBody
	@GetMapping("/book/{bookId}")
	public GetBookCouponTemplateAllResponse getCouponAllForBook(@PathVariable Long bookId, Model model) {
		return couponService.getBookCouponTemplate(bookId, true);

	}

	@ResponseBody
	@GetMapping("/categories")
	public GetCategoryCouponTemplateAllResponse getCouponAllForCategory(@RequestParam List<Long> categoryIdList,
		Model model) {
		return couponService.getCategoryCouponTemplate(categoryIdList, true);
	}

}
