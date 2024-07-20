package store.novabook.front.api.member.member.dto.response;

import org.junit.jupiter.api.Test;
import store.novabook.front.api.member.member.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;

class CreateMemberResponseTest {

	@Test
	void testCreateMemberResponse_Builder() {
		Long expectedId = 123L;

		CreateMemberResponse response = CreateMemberResponse.builder()
			.id(expectedId)
			.build();

		assertThat(response).isNotNull();
		assertThat(response.id()).isEqualTo(expectedId);
	}

	@Test
	void testCreateMemberResponse_FromEntity() {
		Long expectedId = 123L;
		Member member = new Member() {
			@Override
			public Long getId() {
				return expectedId;
			}
		};

		CreateMemberResponse response = CreateMemberResponse.fromEntity(member);

		assertThat(response).isNotNull();
		assertThat(response.id()).isEqualTo(expectedId);
	}

	@Test
	void testCreateMemberResponse_NullId() {
		Long expectedId = null;

		CreateMemberResponse response = CreateMemberResponse.builder()
			.id(expectedId)
			.build();

		assertThat(response).isNotNull();
		assertThat(response.id()).isNull();
	}

	@Test
	void testCreateMemberResponse_FromEntity_NullId() {
		Long expectedId = null;
		Member member = new Member() {
			@Override
			public Long getId() {
				return expectedId;
			}
		};

		CreateMemberResponse response = CreateMemberResponse.fromEntity(member);

		assertThat(response).isNotNull();
		assertThat(response.id()).isNull();
	}
}
