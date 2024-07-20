package store.novabook.front.admin.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.service.CategoryService;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateLimitedCouponTemplateRequest;
import store.novabook.front.api.coupon.service.CouponService;

@RequestMapping("/admin/coupons")
@Controller
@RequiredArgsConstructor
public class AdminCouponController {

	public static final String REDIRECT_ADMIN_COUPONS = "redirect:/admin/coupons";
	private final CouponService couponService;
	private final CategoryService categoryService;
	private static final String PAGE = "0";
	private static final String PAGE_SIZE = "5";

	@GetMapping
	public String getCoupons(Model model, @RequestParam(defaultValue = PAGE) int birthdayPage,
		@RequestParam(defaultValue = PAGE) int limitedPage, @RequestParam(defaultValue = PAGE) int welcomePage,
		@RequestParam(defaultValue = PAGE) int generalPage, @RequestParam(defaultValue = PAGE) int bookPage,
		@RequestParam(defaultValue = PAGE) int categoryPage, @RequestParam(defaultValue = PAGE_SIZE) int size) {

		model.addAttribute("birthdayCoupons",
			couponService.getCouponTemplateAll(CouponType.BIRTHDAY, false, birthdayPage, size, "createdAt,desc"));
		model.addAttribute("welcomeCoupons",
			couponService.getCouponTemplateAll(CouponType.WELCOME, false, welcomePage, size, "createdAt,desc"));
		model.addAttribute("generalCoupons",
			couponService.getCouponTemplateAll(CouponType.GENERAL, false, generalPage, size, "startedAt,desc"));
		model.addAttribute("bookCoupons", couponService.getBookCouponTemplateAll(bookPage, size));
		model.addAttribute("categoryCoupons", couponService.getCategoryCouponTemplateAll(false, categoryPage, size));
		model.addAttribute("limitedCoupons", couponService.getLimitedCouponTemplateAll(
			false, limitedPage, size));

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

	@GetMapping("/limited/form")
	public String getCouponLimitedForm() {
		return "admin/coupon/coupon_limited_form";
	}

	@PostMapping("/common/create")
	public String createCouponTemplateCommon(@Valid @ModelAttribute CreateCouponTemplateRequest couponRequest) {
		couponService.createGeneralTemplateCoupon(couponRequest);
		return REDIRECT_ADMIN_COUPONS;
	}

	@PostMapping("/book/create")
	public String createCouponTemplateBook(@Valid @ModelAttribute CreateBookCouponTemPlateRequest bookCouponRequest) {
		couponService.createBookTemplateCoupon(bookCouponRequest);
		return REDIRECT_ADMIN_COUPONS;
	}

	@PostMapping("/category/create")
	public String createCouponTemplateCategory(@Valid @ModelAttribute CreateCategoryCouponTemplateRequest request) {
		couponService.createCategoryTemplateCoupon(request);
		return REDIRECT_ADMIN_COUPONS;
	}

	@PostMapping("/limited/create")
	public String createCouponTemplateLimited(@Valid @ModelAttribute CreateLimitedCouponTemplateRequest request) {
		couponService.createLimitedTemplateCoupon(request);
		return REDIRECT_ADMIN_COUPONS;
	}

}
