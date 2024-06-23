package com.nhnacademy.novabook_front.api.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.novabook_front.api.order.dto.response.GetWrappingPaperResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WrappingPaperServiceImpl implements WrappingPaperService{
	private final WrappingPaperClient wrappingPaperClient;

	@Override
	public List<GetWrappingPaperResponse> getWrappingPaperAllList() {
		GetWrappingPaperAllResponse response = wrappingPaperClient.getWrappingPaperAll().getBody();
		return response.papers();
	}
}
