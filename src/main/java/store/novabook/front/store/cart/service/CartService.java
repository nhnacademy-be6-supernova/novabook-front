package store.novabook.front.store.cart.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.cart.CartClient;
import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookIdDTO;
import store.novabook.front.api.cart.dto.CartBookListDTO;
import store.novabook.front.api.cart.dto.request.DeleteCartBookListRequest;
import store.novabook.front.api.cart.dto.request.GetBookInfoRequest;
import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
import store.novabook.front.api.cart.dto.response.GetBookInfoResponse;
import store.novabook.front.store.cart.hash.RedisCartHash;
import store.novabook.front.store.cart.repository.RedisCartRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartClient cartClient;
    private final RedisCartRepository redisCartRepository;

    public void addCartBooks(CartBookListDTO request) {
        cartClient.addCartBooks(request);
    }

    public Optional<RedisCartHash> findByCartId(Object cartId){
        return  redisCartRepository.findById(cartId);
    }

    public boolean existCart(Optional<RedisCartHash> redisCartHashOpt){
        return  redisCartHashOpt.isPresent() && Objects.nonNull(redisCartHashOpt.get().getCartBookList());
    }

    public void deleteCartBook(Object cartId, Long bookId) {
        Optional<RedisCartHash> redisCartHashOpt = findByCartId(cartId);
        if (existCart(redisCartHashOpt)) {
            RedisCartHash redisCartHash = redisCartHashOpt.get();
            List<CartBookDTO> updatedCartBookList = redisCartHash.getCartBookList().stream()
                    .filter(cartBook -> !cartBook.bookId().equals(bookId))
                    .toList();
            RedisCartHash updatedRedisCartHash = new RedisCartHash(cartId, updatedCartBookList);
            redisCartRepository.save(updatedRedisCartHash);
        }
        if(cartId instanceof Long){
            cartClient.deleteCartBook(bookId);
        }
    }

    public void deleteCartBooks(Object cartId, DeleteCartBookListRequest request) {
        Optional<RedisCartHash> redisCartHashOpt = findByCartId(cartId);
        if (existCart(redisCartHashOpt)) {
            RedisCartHash redisCartHash = redisCartHashOpt.get();
            List<CartBookDTO> updatedCartBookList = redisCartHash.getCartBookList().stream()
                    .filter(cartBook -> !request.bookIds().contains(cartBook.bookId()))
                    .toList();
            RedisCartHash updatedRedisCartHash = new RedisCartHash(cartId, updatedCartBookList);
            redisCartRepository.save(updatedRedisCartHash);
        }
        if(cartId instanceof Long){
            cartClient.deleteCartBooks(request);
        }

    }

    public void updateCartBookQuantity(Object cartId,UpdateCartBookQuantityRequest request) {
        Optional<RedisCartHash> redisCartHashOpt = findByCartId(cartId);
        if (existCart(redisCartHashOpt)) {
            RedisCartHash redisCartHash = redisCartHashOpt.get();
            List<CartBookDTO> updatedCartBookList = redisCartHash.getCartBookList();
            for (CartBookDTO cartBook : updatedCartBookList) {
                if (cartBook.bookId().equals(request.bookId())) {

                    CartBookDTO updatedCartBook = CartBookDTO.update(request.bookId(), request.quantity(), cartBook);

                    int index = updatedCartBookList.indexOf(cartBook);
                    updatedCartBookList.set(index, updatedCartBook);
                    break;
                }
            }

            redisCartHash.update(updatedCartBookList);
            redisCartRepository.save(redisCartHash);
        }

        if(cartId instanceof Long){
            cartClient.updateCartBook(request);
        }

    }

    public List<CartBookDTO> getCartBookAll(Object cartId) {
        if (notExistCart(cartId)) {
            createCart(cartId);
            return Collections.emptyList();
        }

        Optional<RedisCartHash> redisCartHash = redisCartRepository.findById(cartId);
        if (redisCartHash.isPresent() && Objects.nonNull(redisCartHash.get().getCartBookList())) {
            return redisCartHash.get().getCartBookList();
        }
        else{
            CartBookListDTO cartBookListDTO =  cartClient.getCartBookAllByMemberId().getBody();
            if(!cartBookListDTO.getCartBookList().isEmpty()){
                addCartBooksToRedis(cartId, cartBookListDTO);
            }
        }
        return Collections.emptyList();
    }

    public boolean notExistCart(Object cartId) {
        Optional<RedisCartHash> redisCartHashOpt = redisCartRepository.findById(cartId);
        return redisCartHashOpt.isEmpty();
    }
    public void createCart(Object cartId) {
        RedisCartHash newCart = RedisCartHash.of(cartId);
        redisCartRepository.save(newCart);
    }
    public void deleteCart(Object cartId) {
        redisCartRepository.deleteById(cartId);
    }
    public void addCartBooksToRedis(Object cartId, CartBookListDTO request) {
        Optional<RedisCartHash> redisCartHashOpt = findByCartId(cartId);
        if (existCart(redisCartHashOpt)) {
            RedisCartHash redisCartHash = redisCartHashOpt.get();
            List<CartBookDTO> updatedCartBookList = new ArrayList<>(redisCartHash.getCartBookList());
            updatedCartBookList.addAll(request.getCartBookList());
            RedisCartHash updatedRedisCartHash = RedisCartHash.builder()
                    .cartId(cartId)
                    .cartBookList(updatedCartBookList)
                    .build();
            redisCartRepository.save(updatedRedisCartHash);
        } else {
            RedisCartHash newCart = RedisCartHash.of(cartId, request);
            redisCartRepository.save(newCart);
        }
    }
    public void addCartBook(Object cartId, CartBookDTO request) {
        Optional<RedisCartHash> redisCartHashOpt = findByCartId(cartId);
        if (existCart(redisCartHashOpt)) {
            RedisCartHash redisCartHash = redisCartHashOpt.get();
            List<CartBookDTO> updatedCartBookList = redisCartHash.getCartBookList();

            boolean bookExists = false;
            for (CartBookDTO cartBook : updatedCartBookList) {
                if (cartBook.bookId().equals(request.bookId())) {
                    // 동일한 도서가 이미 존재하면 수량 증가
                    int newQuantity = cartBook.quantity() + request.quantity();
                    updatedCartBookList.remove(cartBook); // 기존 도서 항목 삭제
                    updatedCartBookList.add(
                            CartBookDTO.update(cartBook.bookId(), newQuantity, request)); // 새로운 도서 항목 추가
                    bookExists = true;
                    break;
                }
            }

            // 동일한 도서가 존재하지 않으면 새로 추가
            if (!bookExists) {
                updatedCartBookList.add(request);
            }

            redisCartRepository.save(new RedisCartHash(cartId, updatedCartBookList));

        } else {
            RedisCartHash newCart = RedisCartHash.of(cartId, request);
            redisCartRepository.save(newCart);
        }

        if(cartId instanceof Long){
            cartClient.addCartBook(request);
        }
    }
    public void transferGuestCartToMemberCart(String guestId, Long memberId){
        List<CartBookDTO> cartBookDTOList = getCartBookAll(guestId);
        if(!cartBookDTOList.isEmpty()){
            addCartBooks(new CartBookListDTO(cartBookDTOList)); //db save
            addCartBooksToRedis(memberId, new CartBookListDTO(cartBookDTOList));//redis save
        }
        deleteCart(guestId);
    }
    public int getCartCount(Object cartId) {
        int count = 0;
        Optional<RedisCartHash> redisCartHashOpt = findByCartId(cartId);

        if (redisCartHashOpt.isPresent() && Objects.nonNull(redisCartHashOpt.get().getCartBookList())) {
            count = redisCartHashOpt.get().getCartBookList().size();
        }
        else{
            cartClient.getCartCount();
        }

        return count;
    }

}
