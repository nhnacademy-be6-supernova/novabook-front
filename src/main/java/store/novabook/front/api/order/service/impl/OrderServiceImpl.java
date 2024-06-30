package store.novabook.front.api.order.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import store.novabook.front.api.order.client.WrappingPaperClient;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.OrderViewDTO;

@Service
public class OrderServiceImpl implements OrderService {

	private final WrappingPaperClient wrappingPaperClient;

	public OrderServiceImpl(WrappingPaperClient wrappingPaperClient) {
		this.wrappingPaperClient = wrappingPaperClient;
	}

	@Override
	public void getOrder(List<BookDTO> bookDTOS, Long memberId) {
		// 하나라도 포장 가능하면 내용 보여주기
		boolean isPackage = false;
		for (BookDTO bookDTO : bookDTOS) {
			if(bookDTO.isPackage()) {
				isPackage = true;
			}
		}
		// DTO에 담을 내용
		List<GetWrappingPaperResponse> papers = wrappingPaperClient.getWrappingPaperAllList().getBody().papers();

		OrderViewDTO orderViewDTO = OrderViewDTO.builder()
			.wrappingPapers(papers)
			.build();

	}

}
