package store.novabook.front.api.coupon.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateLimitedCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.GetCouponAllRequest;
import store.novabook.front.api.coupon.dto.response.CreateCouponResponse;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateAllResponse;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetLimitedCouponTemplateResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

/**
 * 쿠폰 클라이언트 인터페이스입니다.
 * 쿠폰 템플릿의 생성 및 조회를 위한 API 호출을 정의합니다.
 */

@FeignClient(name = "gateway-service", path = "/api/v1/coupon", contextId = "couponClient")
public interface CouponClient {

	/**
	 * 모든 일반 쿠폰 템플릿을 조회합니다. 쿠폰 타입도 선택적으로 지정할 수 있습니다.
	 *
	 * @param type 쿠폰 타입 (선택 사항)
	 * @param page 페이지 번호
	 * @param size 페이지 크기
	 * @return 페이지 응답 객체
	 */
	@GetMapping("/templates")
	PageResponse<GetCouponTemplateResponse> getCouponTemplateAll(@RequestParam(required = false) CouponType type,
		@RequestParam(defaultValue = "true") Boolean isValid, @RequestParam int page, @RequestParam int size,
		@RequestParam(required = false) String sort);

	/**
	 * 모든 책 쿠폰 템플릿을 조회합니다.
	 *
	 * @param page 페이지 번호
	 * @param size 페이지 크기
	 * @return 페이지 응답 객체
	 */
	@GetMapping("/templates/book")
	PageResponse<GetBookCouponTemplateResponse> getBookCouponTemplateAll(@RequestParam int page,
		@RequestParam int size);

	@GetMapping("/templates/limited")
	PageResponse<GetLimitedCouponTemplateResponse> getLimitedCouponTemplateAll(@RequestParam boolean isValid,
		@RequestParam int page, @RequestParam int size);

	/**
	 * 모든 카테고리 쿠폰 템플릿을 조회합니다.
	 *
	 * @param page 페이지 번호
	 * @param size 페이지 크기
	 * @return 페이지 응답 객체
	 */
	@GetMapping("/templates/category")
	PageResponse<GetCategoryCouponTemplateResponse> getCategoryCouponTemplateAll(
		@RequestParam(defaultValue = "true") boolean isValid, @RequestParam int page, @RequestParam int size);

	/**
	 * 일반 쿠폰 템플릿을 생성합니다.
	 *
	 * @param request 쿠폰 템플릿 생성 요청 객체
	 * @return API 응답 객체
	 */
	@PostMapping("/templates")
	ApiResponse<CreateCouponResponse> createCouponTemplate(@RequestBody CreateCouponTemplateRequest request);

	@PostMapping("/templates/limited")
	ApiResponse<CreateCouponResponse> createLimitedCouponTemplate(
		@RequestBody CreateLimitedCouponTemplateRequest request);

	/**
	 * 책 쿠폰 템플릿을 생성합니다.
	 *
	 * @param request 책 쿠폰 템플릿 생성 요청 객체
	 * @return API 응답 객체
	 */
	@PostMapping("/templates/book")
	ApiResponse<CreateCouponResponse> createBookCouponTemplate(@RequestBody CreateBookCouponTemPlateRequest request);

	/**
	 * 카테고리 쿠폰 템플릿을 생성합니다.
	 *
	 * @param request 카테고리 쿠폰 템플릿 생성 요청 객체
	 * @return API 응답 객체
	 */
	@PostMapping("/templates/category")
	ApiResponse<CreateCouponResponse> createCategoryCouponTemplate(
		@RequestBody CreateCategoryCouponTemplateRequest request);

	/**
	 * 여러 카테고리 ID로 쿠폰 템플릿을 조회합니다.
	 *
	 * @param categoryIdList 조회할 카테고리의 ID 리스트
	 * @param isValid        유효성 여부
	 * @return 조회된 카테고리 쿠폰 템플릿의 응답
	 */
	@GetMapping(value = "/templates/category/categories", params = "categoryIdList")
	GetCategoryCouponTemplateAllResponse getCategoryCouponTemplateAllByCategoryIdAll(
		@RequestParam("categoryIdList") List<Long> categoryIdList, @RequestParam("isValid") boolean isValid);

	@GetMapping("/templates/book/{bookId}")
	ApiResponse<GetBookCouponTemplateAllResponse> getCouponTemplateByBookId(@PathVariable Long bookId,
		@RequestParam(defaultValue = "true") boolean isValid);

	@PostMapping("/coupons/sufficient")
	ApiResponse<GetCouponAllResponse> getSufficientCouponAll(@Valid @RequestBody GetCouponAllRequest request);

}
