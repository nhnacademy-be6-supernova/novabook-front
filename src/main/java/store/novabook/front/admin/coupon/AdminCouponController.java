package store.novabook.front.admin.coupon;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.CouponClient;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponRequest;
import store.novabook.front.api.coupon.dto.response.CreateCouponResponse;

@RequestMapping("/admin/coupons")
@Controller
@RequiredArgsConstructor
public class AdminCouponController {

	// TODO : 대충 feign 박아둠. 나중에 수정 ㄱ
	private final CouponClient couponClient;

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

	@PostMapping("/common/create")
	public String createCouponCommon(@ModelAttribute CreateCouponRequest couponRequest) {
		ResponseEntity<CreateCouponResponse> response = couponClient.saveGeneralCoupon(couponRequest);
		return "redirect:/admin/coupons";
	}

	@PostMapping("/book/create")
	public String createCouponBook(@ModelAttribute CreateBookCouponRequest bookCouponRequest) {
		ResponseEntity<CreateCouponResponse> response = couponClient.saveBookCoupon(bookCouponRequest);
		return "redirect:/admin/coupons";
	}

	@PostMapping("/category/create")
	public String createCouponCategory(@ModelAttribute CreateCategoryCouponRequest createCategoryCouponRequest) {
		ResponseEntity<CreateCouponResponse> response = couponClient.saveCategoryCoupon(createCategoryCouponRequest);
		return "redirect:/admin/coupons";
	}

}
