package store.novabook.front.api.order.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.api.coupon.client.CouponClient;
import store.novabook.front.api.coupon.dto.request.GetCouponAllRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponResponse;
import store.novabook.front.api.member.coupon.service.MemberCouponClient;
import store.novabook.front.api.order.client.WrappingPaperClient;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.api.point.dto.request.GetPointHistoryRequest;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.api.point.service.PointHistoryClient;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.OrderViewDTO;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final WrappingPaperClient wrappingPaperClient;
	private final CouponClient couponClient;
	private final CategoryClient categoryClient;
	private final MemberCouponClient memberCouponClient;
	private final PointHistoryClient pointHistoryClient;

	@Override
	public OrderViewDTO getOrder(List<BookDTO> bookDTOS, Long memberId) {
		Set<Long> bookIds = new HashSet<>();
		boolean isPackage = false;

		for (BookDTO bookDTO : bookDTOS) {
			bookIds.add(bookDTO.id());
			if (bookDTO.isPackage()) {
				isPackage = true;
			}
		}

		Set<Long> categoryIdList = new HashSet<>();
		bookIds.forEach(bookId -> {
			List<Long> categoryIds = categoryClient.getCategoryByBId(bookId).getBody().categoryIds();
			categoryIdList.addAll(categoryIds);
		});

		List<Long> couponIdList = memberCouponClient.getMemberCoupon().getBody().couponIds();

		List<GetWrappingPaperResponse> papers = wrappingPaperClient.getWrappingPaperAllList().getBody().getWrappingPaperResponse();

		GetCouponAllRequest couponRequest = GetCouponAllRequest.builder()
			.couponIdList(couponIdList)
			.categoryIdList(categoryIdList)
			.bookIdList(bookIds)
			.build();

		List<GetCouponResponse> coupons = couponClient.getSufficientCouponAll(couponRequest)
			.getBody()
			.couponResponseList();

		long myPoint = pointHistoryClient.getPointHistoryListByMemberId(new GetPointHistoryRequest(memberId))
			.getBody()
			.pointHistoryResponseList()
			.stream()
			.mapToLong(GetPointHistoryResponse::pointAmount)
			.sum();

		return OrderViewDTO.builder()
			.isPackable(isPackage)
			.coupons(coupons)
			.wrappingPapers(papers)
			.myPoint(myPoint)
			.build();
	}
}
