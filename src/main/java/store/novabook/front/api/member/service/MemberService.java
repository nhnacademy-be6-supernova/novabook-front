package store.novabook.front.api.member.service;

import store.novabook.front.api.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.dto.CreateMemberResponse;
import store.novabook.front.api.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.dto.LoginMemberResponse;

public interface MemberService {
	CreateMemberResponse createMember(CreateMemberRequest createMemberRequest);

	LoginMemberResponse login(LoginMemberRequest loginMemberRequest);
}
