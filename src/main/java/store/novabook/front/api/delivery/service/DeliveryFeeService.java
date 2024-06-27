package store.novabook.front.api.delivery.service;

import store.novabook.front.api.PageResponse;
import store.novabook.front.api.delivery.dto.CreateDeliveryFeeRequest;
import store.novabook.front.api.delivery.dto.GetDeliveryFeeResponse;

public interface DeliveryFeeService {
	void createDeliveryFee(CreateDeliveryFeeRequest request);

	PageResponse<GetDeliveryFeeResponse> getDeliveryFeeAllPage(int page, int size);
}
