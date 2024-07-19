package store.novabook.front.api.member.grade.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import store.novabook.front.api.member.grade.MemberGradeClient;
import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.common.response.ApiResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MemberGradeServiceImplTest {

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
		// Given
		GetMemberGradeResponse expectedResponse = new GetMemberGradeResponse("Gold");

		// Mocking Service Call
		when(memberGradeClient.getMemberGrade()).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		GetMemberGradeResponse actualResponse = memberGradeService.getMemberGrade();

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(memberGradeClient, times(1)).getMemberGrade();
	}
}
