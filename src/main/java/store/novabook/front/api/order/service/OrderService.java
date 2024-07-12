package store.novabook.front.api.order.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.api.member.member.dto.response.MemberOrderNameReponse;
import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.GetOrdersAdminResponse;
import store.novabook.front.store.order.dto.OrderViewDTO;
import store.novabook.front.store.order.dto.UpdateOrdersAdminRequest;

public interface OrderService {
	OrderViewDTO getOrder(List<BookDTO> bookDTOS, Long memberId);

	PageResponse<GetOrdersAdminResponse> getOrderAllAdmin(int page, int size);

	void update(@PathVariable Long id, @Valid @RequestBody UpdateOrdersAdminRequest request);
	void createOrder(PaymentRequest request);
	boolean isInvalidAccess(Long memberId, UUID orderUUID, Long orderMemberId);

	MemberOrderNameReponse getSuccessView(UUID orderUUID, Long memberId);
}
