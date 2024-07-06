package store.novabook.front.api.book.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "searchBookClient")
public interface BookSearchClient {

}
