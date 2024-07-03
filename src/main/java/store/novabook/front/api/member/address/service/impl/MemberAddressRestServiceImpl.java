package store.novabook.front.api.member.address.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.address.dto.response.ExceedResponse;
import store.novabook.front.api.member.address.service.MemberAddressClient;
import store.novabook.front.api.member.address.service.MemberAddressRestService;

@Service
@RequiredArgsConstructor
public class MemberAddressRestServiceImpl implements MemberAddressRestService {
	private final MemberAddressClient memberAddressClient;

	@Override
	public ExceedResponse isExceedMemberAddressCount() {
		return memberAddressClient.isExceedMemberAddressCount().getBody();
	}

}
