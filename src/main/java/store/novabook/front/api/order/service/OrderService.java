package store.novabook.front.api.order.service;

import java.util.List;
import java.util.UUID;

import store.novabook.front.api.member.member.dto.response.MemberOrderNameReponse;
import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.OrderViewDTO;

public interface OrderService {
	OrderViewDTO getOrder(List<BookDTO> bookDTOS, Long memberId);
	void createOrder(PaymentRequest request);
	boolean isInvalidAccess(Long memberId, UUID orderUUID, Long orderMemberId);

	MemberOrderNameReponse getSuccessView(UUID orderUUID, Long memberId);
}
