package store.novabook.front.api.member.member.service;

import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.request.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberNameRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberNumberRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.dto.response.LoginMemberResponse;

public interface MemberService {
	CreateMemberResponse createMember(CreateMemberRequest createMemberRequest);

	LoginMemberResponse getMember(LoginMemberRequest loginMemberRequest, HttpServletResponse response);

	GetMemberResponse getMemberById(Long memberId);

	void updateMemberName(Long memberId, UpdateMemberNameRequest updateMemberNameRequest);

	void updateMemberNumber(Long memberId, UpdateMemberNumberRequest updateMemberNumberRequest);

	void updateMemberPassword(Long memberId, UpdateMemberPasswordRequest updateMemberPasswordRequest);

	void deleteMember(Long memberId, DeleteMemberRequest deleteMemberRequest);

	GetNewTokenResponse newToken(GetNewTokenRequest getNewTokenRequest);
}
