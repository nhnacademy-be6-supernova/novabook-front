package store.novabook.front.admin.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.service.CategoryService;
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
	private final CategoryService categoryService;
	private static final String PAGE = "0";
	private static final String PAGE_SIZE = "5";
	private static final String REDIRECT_ADMIN_COUPONS = "redirect:/admin/coupons";

	@GetMapping
	public String getCoupons(Model model,
		@RequestParam(defaultValue = PAGE) int birthdayPage,
		@RequestParam(defaultValue = PAGE) int welcomePage,
		@RequestParam(defaultValue = PAGE) int generalPage,
		@RequestParam(defaultValue = PAGE) int bookPage,
		@RequestParam(defaultValue = PAGE) int categoryPage,
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

	@GetMapping("/common/type")
	public String getCouponGeneralForm(Model model, @RequestParam CouponType type) {

		model.addAttribute("couponType", type);

		return "admin/coupon/coupon_common_form";
	}

	@GetMapping("/book/form")
	public String getCouponBookForm() {
		return "redirect:/admin/books";
	}

	@GetMapping("/category/form")
	public String getCouponCategoryForm(Model model) {
		model.addAttribute("categories", categoryService.getCategoryAll());
		return "admin/coupon/coupon_category_form";
	}

	@PostMapping("/common/create")
	public String createCouponTemplateCommon(@ModelAttribute CreateCouponTemplateRequest couponRequest) {
		couponService.createGeneralTemplateCoupon(couponRequest);
		return REDIRECT_ADMIN_COUPONS;
	}

	@PostMapping("/book/create")
	public String createCouponTemplateBook(@ModelAttribute CreateBookCouponTemPlateRequest bookCouponRequest) {
		couponService.createBookTemplateCoupon(bookCouponRequest);
		return REDIRECT_ADMIN_COUPONS;
	}

	@PostMapping("/category/create")
	public String createCouponTemplateCategory(
		@ModelAttribute CreateCategoryCouponTemplateRequest request) {
		couponService.createCategoryTemplateCoupon(request);
		return REDIRECT_ADMIN_COUPONS;
	}

}
