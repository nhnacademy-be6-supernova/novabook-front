package com.nhnacademy.novabook_front.api.book;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Book {

	private Long id;

	private Long bookStatusId;

	private String isbn;

	private String title;

	private String bookIndex;

	private String description;

	private String descriptionDetail;

	private String author;

	private String publisher;

	private LocalDateTime publicationDate;

	int inventory;

	private Long price;

	private Long discountPrice;

	boolean isPackaged;

	private String image;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
