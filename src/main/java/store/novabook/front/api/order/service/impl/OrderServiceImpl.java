package store.novabook.front.api.order.service.impl;

import java.util.List;

import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.api.order.service.WrappingPaperService;
import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.api.point.service.PointService;

public class OrderServiceImpl {
	private final WrappingPaperService wrappingPaperService;
	private final PointService pointService;

	public OrderServiceImpl(WrappingPaperService wrappingPaperService, PointService pointService) {
		this.wrappingPaperService = wrappingPaperService;
		this.pointService = pointService;
	}

	public void getOrder() {
		// TODO : 만약 선택 도서가 모두 포장 불가면, 표시 X
		// 포장지 여부
		List<GetWrappingPaperResponse> wrappingPaperAllList = wrappingPaperService.getWrappingPaperAllList();
		// Point 내역
		GetMemberPointResponse memberPoint = pointService.getMemberPoint();
		// 쿠폰 리스트












	}




}
