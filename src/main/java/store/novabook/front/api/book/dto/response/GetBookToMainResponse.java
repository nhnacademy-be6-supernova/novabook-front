package store.novabook.front.api.book.dto.response;

public record GetBookToMainResponse (
	Long id,
	String title,
	String image,
	Integer price,
	Integer discountPrice

){
}
