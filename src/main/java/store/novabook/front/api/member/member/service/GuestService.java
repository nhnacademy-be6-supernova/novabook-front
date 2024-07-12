package store.novabook.front.api.member.member.service;

import store.novabook.front.store.order.dto.GetGuestOrderHistoryRequest;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;

public interface GuestService {
	GetOrderDetailResponse login(GetGuestOrderHistoryRequest request);
}
