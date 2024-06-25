package store.novabook.front.api.order.service.impl;

import store.novabook.front.api.order.service.WrappingPaperService;

public class OrderServiceImpl {

	private final WrappingPaperService wrappingPaperService;

	public OrderServiceImpl(WrappingPaperService wrappingPaperService) {
		this.wrappingPaperService = wrappingPaperService;
	}

	public void getOrder() {

		// DTO 제작
		wrappingPaperService.getWrappingPaperAllList();





	}




}
