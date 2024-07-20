package store.novabook.front.api.member.member.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.request.DuplicateEmailRequest;
import store.novabook.front.api.member.member.dto.request.DuplicateLoginIdRequest;
import store.novabook.front.api.member.member.dto.response.DuplicateResponse;
import store.novabook.front.api.member.member.dto.response.MemberNameResponse;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.member.member.service.MemberRestService;

@Service
@RequiredArgsConstructor
public class MemberRestServiceImpl implements MemberRestService {
	private final MemberClient memberClient;

	@Override
	public DuplicateResponse isDuplicateLoginId(DuplicateLoginIdRequest request) {
		return memberClient.isDuplicateLoginId(request).getBody();
	}

	@Override
	public DuplicateResponse isDuplicateEmail(DuplicateEmailRequest request) {
		return memberClient.isDuplicateEmail(request).getBody();
	}

	@Override
	public MemberNameResponse getMemberName() {
		return memberClient.getMemberName().getBody();
	}
}
