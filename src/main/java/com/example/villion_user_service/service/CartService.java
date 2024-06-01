package com.example.villion_user_service.service;

import com.example.villion_user_service.client.BooksServiceClient;
import com.example.villion_user_service.domain.entity.CartEntity;
import com.example.villion_user_service.domain.request.RequestCart;
import com.example.villion_user_service.domain.response.ResponseBooks;
import com.example.villion_user_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final BooksServiceClient booksServiceClient;
    ModelMapper mapper = new ModelMapper();

    public void addCart(RequestCart requestCart) {

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CartEntity cartEntity = mapper.map(requestCart, CartEntity.class);
        cartRepository.save(cartEntity);
    }

    //TODO CartEntity 받는걸 Map으로 바꿀지?
    public List<CartEntity> getCart(Long userId) {

        List<CartEntity> allByUserId = cartRepository.findAllByUserId(userId);


        return allByUserId;
    }
}
