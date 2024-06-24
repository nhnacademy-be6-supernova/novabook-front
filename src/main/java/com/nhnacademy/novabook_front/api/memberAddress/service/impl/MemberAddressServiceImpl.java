package com.nhnacademy.novabook_front.api.memberAddress.service.impl;

import org.springframework.stereotype.Service;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.memberAddress.MemberAddressClient;
import com.nhnacademy.novabook_front.api.memberAddress.dto.CreateMemberAddressRequest;
import com.nhnacademy.novabook_front.api.memberAddress.dto.CreateMemberAddressResponse;
import com.nhnacademy.novabook_front.api.memberAddress.service.MemberAddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAddressServiceImpl implements MemberAddressService {
	private final MemberAddressClient memberAddressClient;

	public CreateMemberAddressResponse createMemberAddress(CreateMemberAddressRequest createMemberAddressRequest) {
		ApiResponse<CreateMemberAddressResponse> memberAddress = memberAddressClient.createMemberAddress(
			createMemberAddressRequest);
		return memberAddress.getBody();
	}
}
