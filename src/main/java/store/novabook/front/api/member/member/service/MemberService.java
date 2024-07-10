package store.novabook.front.api.member.member.service;

import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;

public interface MemberService {
	CreateMemberResponse createMember(CreateMemberRequest createMemberRequest);

	String login(LoginMembersRequest loginMembersRequest, HttpServletResponse response);

	GetMemberResponse getMemberById();

	void updateMember(UpdateMemberRequest updateMemberRequest);

	void updateMemberPassword(UpdateMemberPasswordRequest updateMemberPasswordRequest);

	void deleteMember(DeleteMemberRequest deleteMemberRequest);

	GetNewTokenResponse newToken(GetNewTokenRequest getNewTokenRequest);

	void logout();

}
