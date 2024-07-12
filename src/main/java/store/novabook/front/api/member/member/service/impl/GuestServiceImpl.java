package store.novabook.front.api.member.member.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.service.GuestClient;
import store.novabook.front.api.member.member.service.GuestService;
import store.novabook.front.store.order.dto.GetGuestOrderHistoryRequest;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

	private final GuestClient guestClient;

	@Override
	public GetOrderDetailResponse login(GetGuestOrderHistoryRequest request) {
		return guestClient.getOrder(request).getBody();
	}
}
