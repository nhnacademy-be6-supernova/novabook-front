package store.novabook.front.api.order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.novabook.front.api.category.dto.response.GetCategoryIdsByBookIdResponse;
import store.novabook.front.api.coupon.dto.request.GetCouponAllRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressListResponse;
import store.novabook.front.api.member.coupon.dto.GetCouponIdsResponse;
import store.novabook.front.api.order.dto.response.GetWrappingPaperAllResponse;
import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.common.response.ApiResponse;

@Service
@RequiredArgsConstructor
public class WebClientService {

	public static final String GATEWAY_SERVICE = "http://localhost:9777/";
	private final WebClient webClient;

	public Mono<Set<Long>> fetchCategoryIdsAsync(Set<Long> bookIds) {
		String url = GATEWAY_SERVICE + "api/v1/store/categories/book/{bookId}";
		return Flux.fromIterable(bookIds)
			.flatMap(bookId -> webClient.get()
				.uri(url, bookId)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<ApiResponse<GetCategoryIdsByBookIdResponse>>() {
				})
				.flatMap(response -> {
					GetCategoryIdsByBookIdResponse body = response.getBody();
					if (body == null) {
						return Mono.error(
							new NullPointerException("CategoryResponse body is null for bookId: " + bookId));
					}
					List<Long> categoryIds = body.categoryIds();
					return Mono.just(categoryIds);
				}))
			.collect(HashSet::new, Set::addAll);
	}

	public Mono<ApiResponse<GetWrappingPaperAllResponse>> getWrappingPaperAllList() {
		String url = GATEWAY_SERVICE + "api/v1/store/orders/wrapping-papers";
		return webClient.get()
			.uri(url)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<ApiResponse<GetWrappingPaperAllResponse>>() {
			});
	}

	public Mono<ApiResponse<GetDeliveryFeeResponse>> getRecentDeliveryFee() {
		String url = GATEWAY_SERVICE + "api/v1/store/orders/delivery-fee/recent";
		return webClient.get()
			.uri(url)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<ApiResponse<GetDeliveryFeeResponse>>() {
			});
	}

	public Mono<ApiResponse<GetCouponIdsResponse>> fetchCouponIdsAsync() {
		String url = GATEWAY_SERVICE + "api/v1/store/members/coupons";
		return webClient.get()
			.uri(url)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<ApiResponse<GetCouponIdsResponse>>() {
			});
	}

	public Mono<ApiResponse<GetMemberPointResponse>> getMemberPointTotal() {
		String url = GATEWAY_SERVICE + "api/v1/store/point/histories/member/point";
		return webClient.get()
			.uri(url)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<ApiResponse<GetMemberPointResponse>>() {
			});
	}

	public Mono<ApiResponse<GetMemberAddressListResponse>> getMemberAddressAll() {
		String url = GATEWAY_SERVICE + "api/v1/store/addresses";
		return webClient.get()
			.uri(url)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<ApiResponse<GetMemberAddressListResponse>>() {
			});
	}

	public Mono<ApiResponse<GetCouponAllResponse>> getSufficientCouponAll(GetCouponAllRequest couponRequest) {
		String url = GATEWAY_SERVICE + "api/v1/coupon/coupons/sufficient";
		return webClient.post()
			.uri(url)
			.body(Mono.just(couponRequest), GetCouponAllRequest.class)
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<ApiResponse<GetCouponAllResponse>>() {
			});
	}
}
