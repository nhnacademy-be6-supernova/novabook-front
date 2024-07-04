package store.novabook.front.store.order.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import store.novabook.front.store.book.dto.BookIdAndQuantityDTO;
import store.novabook.front.store.book.dto.BookListDTO;

@RedisHash("OrderForm")
public record OrderTemporaryForm(
	@Id
	Long memberId,
	List<BookIdAndQuantityDTO> books,
	Long wrappingPaperId,
	Long couponIdl,
	long usePointAmount,
	LocalDate deliveryDate,
	OrderSenderInfo orderSenderInfo,
	OrderReceiverInfo orderReceiverInfo
) {}
