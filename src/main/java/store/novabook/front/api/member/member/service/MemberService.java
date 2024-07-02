package store.novabook.front.api.member.member.service;

import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.request.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.dto.response.LoginMemberResponse;

public interface MemberService {
	CreateMemberResponse createMember(CreateMemberRequest createMemberRequest);

	LoginMemberResponse getMember(LoginMemberRequest loginMemberRequest, HttpServletResponse response);

	GetMemberResponse getMemberById(Long memberId);

	void updateMember(Long memberId, UpdateMemberRequest updateMemberRequest);

	void updateMemberPassword(Long memberId, UpdateMemberPasswordRequest updateMemberPasswordRequest);

	void deleteMember(Long memberId, DeleteMemberRequest deleteMemberRequest);

}
