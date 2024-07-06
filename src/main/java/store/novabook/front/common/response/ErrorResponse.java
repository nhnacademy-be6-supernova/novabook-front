package store.novabook.front.common.response;

import java.io.Serializable;

import store.novabook.front.common.exception.ErrorCode;

/**
 * 에러 응답을 나타내는 레코드입니다.
 * 오류 코드와 메시지를 포함합니다.
 */
public record ErrorResponse(ErrorCode errorCode, String message) implements Serializable {
}
