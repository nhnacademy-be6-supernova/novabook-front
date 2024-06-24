package com.nhnacademy.novabook_front.api.order.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.novabook_front.api.PageResponse;
import com.nhnacademy.novabook_front.api.order.dto.request.CreateWrappingPaperRequest;
import com.nhnacademy.novabook_front.api.order.dto.request.UpdateWrappingPaperRequest;
import com.nhnacademy.novabook_front.api.order.dto.response.GetWrappingPaperResponse;
import com.nhnacademy.novabook_front.api.order.dto.response.GetWrappingPaperAllResponse;
import com.nhnacademy.novabook_front.api.order.client.WrappingPaperClient;
import com.nhnacademy.novabook_front.api.order.service.WrappingPaperService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WrappingPaperServiceImpl implements WrappingPaperService {
	private final WrappingPaperClient wrappingPaperClient;

	//List 전체 조회
	@Override
	public List<GetWrappingPaperResponse> getWrappingPaperAllList() {
		GetWrappingPaperAllResponse response = wrappingPaperClient.getWrappingPaperAllList().getBody();
		return response.papers();
	}

	// 페이지 전체 조회
	public PageResponse<GetWrappingPaperResponse> getWrappingPaperAllPage(int page, int size) {
		return wrappingPaperClient.getWrappingPaperAllPage(page, size);
	}

	//단건 조회
	public GetWrappingPaperResponse getWrappingPaper(Long id) {
		return wrappingPaperClient.getWrappingPaper(id).getBody();
	}

	//생성
	public void createWrappingPaper(CreateWrappingPaperRequest request) {
		wrappingPaperClient.createWrappingPaper(request);
	}

	//수정
	public void updateWrappingPaper(Long id, UpdateWrappingPaperRequest request) {
		wrappingPaperClient.putWrappingPaper(request, id);
	}
}
