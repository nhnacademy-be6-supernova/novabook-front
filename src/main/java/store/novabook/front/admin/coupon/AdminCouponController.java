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
			couponService.getCouponTemplateAll(CouponType.BIRTHDAY, false, birthdayPage, size));
		model.addAttribute("welcomeCoupons",
			couponService.getCouponTemplateAll(CouponType.WELCOME, false, welcomePage, size));
		model.addAttribute("generalCoupons",
			couponService.getCouponTemplateAll(CouponType.GENERAL, false, generalPage, size));
		model.addAttribute("bookCoupons", couponService.getBookCouponTemplateAll(bookPage, size));
		model.addAttribute("categoryCoupons", couponService.getCategoryCouponTemplateAll(false, categoryPage, size));
		model.addAttribute("limitedCoupons", couponService.getLimitedCouponTemplateAll(false, limitedPage, size));

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
		return "redirect:/admin/coupons";
	}

	@PostMapping("/book/create")
	public String createCouponTemplateBook(@Valid @ModelAttribute CreateBookCouponTemPlateRequest bookCouponRequest) {
		couponService.createBookTemplateCoupon(bookCouponRequest);
		return "redirect:/admin/coupons";
	}

	@PostMapping("/category/create")
	public String createCouponTemplateCategory(@Valid @ModelAttribute CreateCategoryCouponTemplateRequest request) {
		couponService.createCategoryTemplateCoupon(request);
		return "redirect:/admin/coupons";
	}

	@PostMapping("/limited/create")
	public String createCouponTemplateLimited(@Valid @ModelAttribute CreateLimitedCouponTemplateRequest request) {
		couponService.createLimitedTemplateCoupon(request);
		return "redirect:/admin/coupons";
	}

}
