package store.novabook.front.api.member.member.service;

import store.novabook.front.api.member.member.dto.request.DuplicateEmailRequest;
import store.novabook.front.api.member.member.dto.request.DuplicateLoginIdRequest;
import store.novabook.front.api.member.member.dto.response.DuplicateResponse;
import store.novabook.front.api.member.member.dto.response.MemberNameResponse;

public interface MemberRestService {
	DuplicateResponse isDuplicateLoginId(DuplicateLoginIdRequest request);

	DuplicateResponse isDuplicateEmail(DuplicateEmailRequest request);

	MemberNameResponse getMemberName();
}
