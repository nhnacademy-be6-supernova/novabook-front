package store.novabook.front.admin.coupon;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.api.coupon.CouponClient;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponRequest;
import store.novabook.front.api.coupon.dto.response.CreateCouponResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;
import store.novabook.front.api.coupon.service.CouponService;

@RequestMapping("/admin/coupons")
@Controller
@RequiredArgsConstructor
public class AdminCouponController {
 	private final CouponService couponService;
	// TODO : 대충 feign 박아둠. 나중에 수정 ㄱ
	private final CouponClient couponClient;
	private static final String PAGE_SIZE = "5";
	@GetMapping
	public String getCoupons(Model model,
		@RequestParam(defaultValue = "1") int birthdayPage,
		@RequestParam(defaultValue = "1") int welcomePage,
		@RequestParam(defaultValue = "1") int generalPage,
		@RequestParam(defaultValue = "1") int bookPage,
		@RequestParam(defaultValue = "1") int categoryPage,
		@RequestParam(defaultValue = PAGE_SIZE) int size) {

		PageResponse<GetCouponTemplateResponse> birthdayCoupons = couponService.getCouponTemplateAll(CouponType.BIRTHDAY, birthdayPage-1, size);
		model.addAttribute("birthdayCoupons",birthdayCoupons);
		model.addAttribute("welcomeCoupons",couponService.getCouponTemplateAll(CouponType.WELCOME, welcomePage-1, size));
		model.addAttribute("generalCoupons",couponService.getCouponTemplateAll(CouponType.GENERAL, generalPage-1, size));
		model.addAttribute("bookCoupons",couponService.getBookCouponTemplateAll(bookPage-1, size));
		model.addAttribute("categoryCoupons",couponService.getCategoryCouponTemplateAll(categoryPage-1, size));


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
