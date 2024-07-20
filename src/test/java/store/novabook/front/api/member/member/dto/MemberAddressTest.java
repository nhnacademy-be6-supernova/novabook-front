package store.novabook.front.api.member.member.dto;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import store.novabook.front.api.member.address.domain.MemberAddress;

class MemberAddressTest {

	@Test
	void testMemberAddress() {
		Long id = 1L;
		String nickname = "Home";
		String memberAddressDetail = "123 Main St, Apt 4";
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime updatedAt = LocalDateTime.now();
		Long streetAddressId = 10L;
		Long memberId = 100L;

		MemberAddress memberAddress = Mockito.spy(new MemberAddress());
		Mockito.doReturn(id).when(memberAddress).getId();
		Mockito.doReturn(nickname).when(memberAddress).getNickname();
		Mockito.doReturn(memberAddressDetail).when(memberAddress).getMemberAddressDetail();
		Mockito.doReturn(createdAt).when(memberAddress).getCreatedAt();
		Mockito.doReturn(updatedAt).when(memberAddress).getUpdatedAt();
		Mockito.doReturn(streetAddressId).when(memberAddress).getStreetAddressId();
		Mockito.doReturn(memberId).when(memberAddress).getMemberId();

		assertThat(memberAddress.getId()).isEqualTo(id);
		assertThat(memberAddress.getNickname()).isEqualTo(nickname);
		assertThat(memberAddress.getMemberAddressDetail()).isEqualTo(memberAddressDetail);
		assertThat(memberAddress.getCreatedAt()).isEqualTo(createdAt);
		assertThat(memberAddress.getUpdatedAt()).isEqualTo(updatedAt);
		assertThat(memberAddress.getStreetAddressId()).isEqualTo(streetAddressId);
		assertThat(memberAddress.getMemberId()).isEqualTo(memberId);
	}
}
