package store.novabook.front.api.book.dto.request;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.ImageProcessingException;

@Builder
public record CreateReviewRequest(
	@NotBlank(message = "content 값은 필수 입니다 ")
	String content,
	@NotNull(message = "score는 필수 값입니다.")
	Integer score,
	List<ReviewImageDTO> reviewImageDTOs
) {
	public void setReviewImageDTOs(List<MultipartFile> reviewImages) {
		reviewImages.forEach(reviewImage -> reviewImageDTOs.add(convertToDTO(reviewImage)));
	}

	private ReviewImageDTO convertToDTO(MultipartFile reviewImage) {
		try {
			return ReviewImageDTO.builder()
				.fileName(reviewImage.getOriginalFilename())
				.fileType(reviewImage.getContentType())
				.data(Base64.getEncoder().encodeToString(reviewImage.getBytes()))
			.build();
		} catch (IOException e) {
			throw new ImageProcessingException(ErrorCode.IMAGE_FILE_SAVE_FAIL);
		}
	}
}