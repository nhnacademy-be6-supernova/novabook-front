package store.novabook.front.common.response;

/**
 * 에러 응답을 나타내는 레코드입니다.
 * 오류 코드와 메시지를 포함합니다.
 */
public record ErrorResponse(String errorCode, String message) {
}
