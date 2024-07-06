package store.novabook.front.api.member.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaycoLogoutResponse {

	@JsonProperty("rtn_cd")
	private int returnCode;

	@JsonProperty("rtn_data")
	private PaycoRtnData rtnData;

	@JsonProperty("rtn_msg")
	private String returnMessage;

	public static class PaycoRtnData {
		@JsonProperty("loginStatus")
		private int loginStatus;
	}
}
