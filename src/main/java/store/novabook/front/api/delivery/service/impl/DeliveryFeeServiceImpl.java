package store.novabook.front.api.delivery.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.delivery.client.DeliveryClient;
import store.novabook.front.api.delivery.dto.CreateDeliveryFeeRequest;
import store.novabook.front.api.delivery.dto.GetDeliveryFeeResponse;
import store.novabook.front.api.delivery.service.DeliveryFeeService;
import store.novabook.front.common.response.PageResponse;

@Service
@RequiredArgsConstructor
public class DeliveryFeeServiceImpl implements DeliveryFeeService {
	private final DeliveryClient deliveryClient;

	@Override
	public void createDeliveryFee(CreateDeliveryFeeRequest request) {
		deliveryClient.createDeliveryFee(request);
	}

	@Override
	public PageResponse<GetDeliveryFeeResponse> getDeliveryFeeAllPage(int page, int size) {
		return deliveryClient.getDeliveryAllPage(page, size);
	}
}
