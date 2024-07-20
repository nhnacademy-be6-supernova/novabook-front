package store.novabook.front.common.exception;

public class BadGatewayException extends NovaException {
	public BadGatewayException(ErrorCode errorCode) {
		super(errorCode);
	}
}
