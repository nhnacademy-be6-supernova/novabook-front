package store.novabook.front.api.member.grade.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.MemberGradeClient;
import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberGradeServiceImpl implements MemberGradeService {
	private final MemberGradeClient memberGradeClient;

	public GetMemberGradeResponse getMemberGrade(Long memberId) {
		return memberGradeClient.getMemberGrade(memberId).getBody();
	}
}
