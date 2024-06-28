package store.novabook.front.api.member.member.service;

import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.api.member.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;

public interface MemberService {
	CreateMemberResponse createMember(CreateMemberRequest createMemberRequest);

	LoginMemberResponse getMember(LoginMemberRequest loginMemberRequest, HttpServletResponse response);

}
