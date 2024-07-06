package store.novabook.front.api.member.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaycoLoginApiResponse {

	@JsonProperty("header")
	private PaycoLoginApiHeader header;
	private PaycoLoginApiData data;

	@Getter
	@Setter
	public static class PaycoLoginApiHeader {
		@JsonProperty("isSuccessful")
		private boolean isSuccessful;
		private int resultCode;
		private String resultMessage;
	}

	@Getter
	@Setter
	public static class PaycoLoginApiData {
		private PaycoLoginApiDataMember member;

		@Getter
		@Setter
		public static class PaycoLoginApiDataMember {
			@JsonProperty("idNo")
			private String idNo;
		}
	}
}
