package store.novabook.front.api.order.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.api.order.client.WrappingPaperClient;
import store.novabook.front.api.order.dto.request.CreateWrappingPaperRequest;
import store.novabook.front.api.order.dto.request.UpdateWrappingPaperRequest;
import store.novabook.front.api.order.dto.response.GetWrappingPaperAllResponse;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.api.order.service.WrappingPaperService;

@Service
@RequiredArgsConstructor
public class WrappingPaperServiceImpl implements WrappingPaperService {

	private final WrappingPaperClient wrappingPaperClient;

	@Override
	public List<GetWrappingPaperResponse> getWrappingPaperAllList() {
		GetWrappingPaperAllResponse response = wrappingPaperClient.getWrappingPaperAllList().getBody();
		return response.papers();
	}

	@Override
	public PageResponse<GetWrappingPaperResponse> getWrappingPaperAllPage(int page, int size) {
		return wrappingPaperClient.getWrappingPaperAllPage(page, size);
	}

	@Override
	public GetWrappingPaperResponse getWrappingPaper(Long id) {
		return wrappingPaperClient.getWrappingPaper(id).getBody();
	}

	@Override
	public void createWrappingPaper(CreateWrappingPaperRequest request) {
		wrappingPaperClient.createWrappingPaper(request);
	}

	@Override
	public void updateWrappingPaper(Long id, UpdateWrappingPaperRequest request) {
		wrappingPaperClient.putWrappingPaper(request, id);
	}
}
