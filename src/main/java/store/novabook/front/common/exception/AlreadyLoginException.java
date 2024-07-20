package store.novabook.front.common.exception;

public class AlreadyLoginException extends NovaException {
	public AlreadyLoginException(ErrorCode errorCode) {
		super(errorCode);
	}
}
