package store.novabook.front.api.member.grade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.impl.MemberGradeServiceImpl;
import store.novabook.front.common.response.ApiResponse;

class MemberGradeServiceImplTest {

	@Mock
	private MemberGradeClient memberGradeClient;

	@InjectMocks
	private MemberGradeServiceImpl memberGradeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetMemberGrade() {
		GetMemberGradeResponse expectedResponse = new GetMemberGradeResponse("Gold");

		when(memberGradeClient.getMemberGrade()).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		GetMemberGradeResponse actualResponse = memberGradeService.getMemberGrade();

		assertEquals(expectedResponse, actualResponse);
		verify(memberGradeClient, times(1)).getMemberGrade();
	}
}
