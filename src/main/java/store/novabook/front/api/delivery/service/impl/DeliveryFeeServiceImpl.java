package store.novabook.front.api.delivery.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.delivery.client.DeliveryFeeClient;
import store.novabook.front.api.delivery.dto.request.CreateDeliveryFeeRequest;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.delivery.service.DeliveryFeeService;
import store.novabook.front.common.response.PageResponse;

@Service
@RequiredArgsConstructor
public class DeliveryFeeServiceImpl implements DeliveryFeeService {
	private final DeliveryFeeClient deliveryFeeClient;

	@Override
	public void createDeliveryFee(CreateDeliveryFeeRequest request) {
		deliveryFeeClient.createDeliveryFee(request);
	}

	@Override
	public PageResponse<GetDeliveryFeeResponse> getDeliveryFeeAllPage(int page, int size) {
		return deliveryFeeClient.getDeliveryAllPage(page, size);
	}
}
