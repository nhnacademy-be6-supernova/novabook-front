package store.novabook.front.common.exception;

public class InternalServerException extends NovaException {
	public InternalServerException(ErrorCode errorCode) {
		super(errorCode);
	}
}
