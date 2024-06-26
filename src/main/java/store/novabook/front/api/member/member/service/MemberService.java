package store.novabook.front.api.member.member.service;

import java.util.Map;

import store.novabook.front.api.member.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;

public interface MemberService {
	CreateMemberResponse createMember(CreateMemberRequest createMemberRequest);

	// LoginMemberResponse login(LoginMemberRequest loginMemberRequest);

	LoginMemberResponse getMember(LoginMemberRequest loginMemberRequest);
}
