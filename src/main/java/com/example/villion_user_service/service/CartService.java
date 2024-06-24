package com.example.villion_user_service.service;

import com.example.villion_user_service.client.ProductServiceClient;
import com.example.villion_user_service.domain.entity.CartEntity;
import com.example.villion_user_service.domain.request.RequestCart;
import com.example.villion_user_service.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductServiceClient booksServiceClient;
    ModelMapper mapper = new ModelMapper();

    public void addCart(RequestCart requestCart) {

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CartEntity cartEntity = mapper.map(requestCart, CartEntity.class);
        cartRepository.save(cartEntity);
    }

    public Map<Long, CartEntity> getCart(Long userId) {
        List<CartEntity> allByUserId = cartRepository.findAllByUserId(userId);


        Map<Long, CartEntity> cartMap = new HashMap<>();

        for (CartEntity cartEntity : allByUserId) {
            cartMap.put(cartEntity.getProductId(), cartEntity);
        }
        return cartMap;
    }

//    장바구니 선택 삭제
    @Transactional
    public void deleteCart(Long userId, Long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }

//    장바구니 전체 삭제
    @Transactional
    public void deleteAllCart(Long userId) {
        cartRepository.deleteAllByUserId(userId);
    }

}
