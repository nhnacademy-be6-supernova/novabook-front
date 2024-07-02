package store.novabook.front.api.member.member.service.impl;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.request.DuplicateEmailRequest;
import store.novabook.front.api.member.member.dto.request.DuplicateLoginIdRequest;
import store.novabook.front.api.member.member.dto.response.DuplicateResponse;
import store.novabook.front.api.member.member.service.MemberRestClient;
import store.novabook.front.api.member.member.service.MemberRestService;

@Service
@RequiredArgsConstructor
public class MemberRestServiceImpl implements MemberRestService {
	private final MemberRestClient memberRestClient;

	@Override
	public DuplicateResponse isDuplicateLoginId(DuplicateLoginIdRequest request) {
		return memberRestClient.isDuplicateLoginId(request).getBody();
	}

	@Override
	public DuplicateResponse isDuplicateEmail(DuplicateEmailRequest request) {
		return memberRestClient.isDuplicateEmail(request).getBody();
	}
}
