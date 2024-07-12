package store.novabook.front.api.member.member.service;

import jakarta.validation.Valid;
import store.novabook.front.store.order.dto.GetGuestOrderHistoryRequest;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;

public interface GuestService {
	GetOrderDetailResponse login(@Valid GetGuestOrderHistoryRequest request);
}
