package store.novabook.front.api.delivery.service;

import store.novabook.front.api.delivery.dto.request.CreateDeliveryFeeRequest;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.common.response.PageResponse;

public interface DeliveryFeeService {
	void createDeliveryFee(CreateDeliveryFeeRequest request);

	PageResponse<GetDeliveryFeeResponse> getDeliveryFeeAllPage(int page, int size);
}
