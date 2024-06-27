package store.novabook.front.admin.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;
import store.novabook.front.api.coupon.service.CouponService;
import store.novabook.front.common.response.PageResponse;

@RequestMapping("/admin/coupons")
@Controller
@RequiredArgsConstructor
public class AdminCouponController {
	private final CouponService couponService;
	// TODO : 대충 feign 박아둠. 나중에 수정 ㄱ
	private static final String PAGE_SIZE = "5";

	@GetMapping
	public String getCoupons(Model model,
		@RequestParam(defaultValue = "0") int birthdayPage,
		@RequestParam(defaultValue = "0") int welcomePage,
		@RequestParam(defaultValue = "0") int generalPage,
		@RequestParam(defaultValue = "0") int bookPage,
		@RequestParam(defaultValue = "0") int categoryPage,
		@RequestParam(defaultValue = PAGE_SIZE) int size) {

		PageResponse<GetCouponTemplateResponse> birthdayCoupons = couponService.getCouponTemplateAll(
			CouponType.BIRTHDAY, birthdayPage - 1, size);
		model.addAttribute("birthdayCoupons", birthdayCoupons);
		model.addAttribute("welcomeCoupons",
			couponService.getCouponTemplateAll(CouponType.WELCOME, welcomePage, size));
		model.addAttribute("generalCoupons",
			couponService.getCouponTemplateAll(CouponType.GENERAL, generalPage, size));
		model.addAttribute("bookCoupons",
			couponService.getBookCouponTemplateAll(bookPage, size));
		model.addAttribute("categoryCoupons",
			couponService.getCategoryCouponTemplateAll(categoryPage, size));

		return "admin/coupon/coupon_list";
	}

	@GetMapping("/common/{type}")
	public String getCouponGeneralForm(Model model, @PathVariable String type) {
		CouponType couponType = switch (type.toUpperCase()) {
			case "WELCOME" -> CouponType.WELCOME;
			case "BIRTHDAY" -> CouponType.BIRTHDAY;
			default -> CouponType.GENERAL;
		};

		model.addAttribute("couponType", couponType);

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
	public String createCouponTemplateCommon(@ModelAttribute CreateCouponTemplateRequest couponRequest) {
		couponService.createGeneralTemplateCoupon(couponRequest);
		return "redirect:/admin/coupons";
	}

	@PostMapping("/book/create")
	public String createCouponTemplateBook(@ModelAttribute CreateBookCouponTemPlateRequest bookCouponRequest) {
		couponService.createBookTemplateCoupon(bookCouponRequest);
		return "redirect:/admin/coupons";
	}

	@PostMapping("/category/create")
	public String createCouponTemplateCategory(
		@ModelAttribute CreateCategoryCouponTemplateRequest createCategoryCouponTemplateRequest) {
		couponService.createCategoryTemplateCoupon(createCategoryCouponTemplateRequest);
		return "redirect:/admin/coupons";
	}

}
