package com.nhnacademy.novabook_front.api.order.service;

import java.util.List;

import com.nhnacademy.novabook_front.api.order.dto.response.GetWrappingPaperResponse;

public interface WrappingPaperService {
	List<GetWrappingPaperResponse> getWrappingPaperAllList();
}
