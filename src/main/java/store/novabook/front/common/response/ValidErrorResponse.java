package store.novabook.front.common.response;

import java.util.Map;

import org.springframework.validation.FieldError;

/**
 * 유효성 검사 오류에 대한 응답을 나타내는 레코드입니다.
 * 오류 코드, 메시지 및 필드별 오류 메시지 맵을 포함합니다.
 */
public record ValidErrorResponse(String errorCode, String message, Map<String, String> result) {

	/**
	 * 주어진 {@link FieldError}에 대한 오류 메시지를 반환합니다.
	 *
	 * @param error 필드 오류
	 * @return 오류 메시지, 오류 메시지가 null인 경우 기본 메시지
	 */
	private static String getFieldErrorMessage(FieldError error) {
		String message = error.getDefaultMessage();
		if (message == null) {
			return "잘못된 요청입니다.";
		}
		return message;
	}
}
