package store.novabook.front.api.member.member.service;

import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.api.member.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.GetMemberResponse;
import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;
import store.novabook.front.api.member.member.dto.UpdateMemberNameRequest;
import store.novabook.front.api.member.member.dto.UpdateMemberNumberRequest;
import store.novabook.front.api.member.member.dto.UpdateMemberPasswordRequest;

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
