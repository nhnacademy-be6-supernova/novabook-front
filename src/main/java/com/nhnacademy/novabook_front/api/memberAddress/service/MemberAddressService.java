package com.nhnacademy.novabook_front.api.memberAddress.service;

import com.nhnacademy.novabook_front.api.memberAddress.dto.CreateMemberAddressRequest;
import com.nhnacademy.novabook_front.api.memberAddress.dto.CreateMemberAddressResponse;

public interface MemberAddressService {
	CreateMemberAddressResponse createMemberAddress(CreateMemberAddressRequest createMemberAddressRequest);
}
