package store.novabook.front.common.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {

	private Map<String, Object> header = new HashMap<>();
	private T body;

	@JsonCreator
	public ApiResponse(@JsonProperty("resultMessage") String resultMessage,
		@JsonProperty("isSuccessful") boolean isSuccessful, @JsonProperty("body") T body) {
		this.header = new HashMap<>();
		this.header.put("resultMessage", resultMessage);
		this.header.put("isSuccessful", isSuccessful);
		this.body = body;
	}

	public static <T> ApiResponse<T> success(T body) {
		return new ApiResponse<>("SUCCESS", true, body);
	}

	public void addHeader(String key, Object value) {
		this.header.put(key, value);
	}

	public Map<String, Object> getHeader() {
		return header;
	}

	public String getHeader(String key) {
		return header.get(key).toString();
	}

	public boolean isSuccessful() {
		Object value = getHeader().get("isSuccessful");
		if (value instanceof Boolean isSuccessful) {
			return isSuccessful;
		}
		throw new IllegalStateException();
	}

	/**
	 * 응답 본문이 ErrorResponse 또는 ValidErrorResponse인지 확인합니다.
	 *
	 * @return 에러 메시지, 에러가 아닌 경우 null
	 */
	public String getErrorMessage() {
		if (body instanceof ErrorResponse errorResponse) {
			return errorResponse.message();
		} else if (body instanceof ValidErrorResponse validErrorResponse) {
			return validErrorResponse.message();
		}
		return null;
	}
}
