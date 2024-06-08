package com.example.villion_user_service.controller;

import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.entity.CartEntity;
import com.example.villion_user_service.domain.entity.ProductEntity;
import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.request.RequestAddProduct;
import com.example.villion_user_service.domain.request.RequestCart;
import com.example.villion_user_service.domain.request.RequestSignup;
import com.example.villion_user_service.domain.request.RequestUser;
import com.example.villion_user_service.domain.response.ResponseLogin;
import com.example.villion_user_service.domain.response.ResponseUser;
import com.example.villion_user_service.repository.CartRepository;
import com.example.villion_user_service.service.CartService;
import com.example.villion_user_service.service.ProductService;
import com.example.villion_user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;


    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseLogin> createUser(@RequestBody RequestSignup requestSignup) {
        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(requestSignup, UserDto.class); // RequestLogin 객체를 UserDto로 전달

        ResponseLogin responseLogin = mapper.map(userDto, ResponseLogin.class);

        userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseLogin);
    }



    // 로그인
//    @PostMapping("/login")
//    public void login (RequestLogin requestLogin) {
//
//
//    }



    // 사용자 ID로 조회
    @GetMapping("/findByID/{userId}")
    public ResponseEntity<ResponseUser> findByID(@PathVariable("userId") Long userId) {
        Optional<UserEntity> userEntityOptional = userService.findByUserId(userId);

        // TODO 이거 넣어야 하나?
        if (userEntityOptional.isEmpty()) { // Optional 내부에 실제 객체가 있는지 확인
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserEntity userEntity = userEntityOptional.get();

        ResponseUser returnValue = new ModelMapper().map(userEntity, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    // 도서관 정보 수정
    @PutMapping("/updateLibrary/{userId}")
    public ResponseEntity<ResponseUser> updateLibrary(@PathVariable("userId") Long userId, @RequestBody RequestUser requestUser) {
        UserEntity userEntity = userService.updateLibrary(userId, requestUser);

        // TODO 이거 넣어야 하나?
        if (userEntity != null) { // Optional 내부에 실제 객체가 있는지 확인
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ResponseUser returnValue = new ModelMapper().map(userEntity, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }


    // 찜 도서관




    // 찜 도서




    // 장바구니 담기
    @PostMapping("/addCart")
    public void addCart(@RequestBody RequestCart requestCart) {
        cartService.addCart(requestCart);

    }

    // 장바구니 보여주기
    @GetMapping("/addCart/{userId}")
    public Map<Long, CartEntity> getCart(@PathVariable("userId") Long userId) {
        Map<Long, CartEntity> cart = cartService.getCart(userId);
        return cart;
    }

    // 장바구니 선택 비우기
    @PostMapping("/deleteCart/{userId}/{productId}")
    public void deleteCart(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
        cartService.deleteCart(userId, productId);
    }

    // 장바구니 전체 비우기
    @PostMapping("/deleteAllCart/{userId}")
    public void deleteAllCart(@PathVariable("userId") Long userId) {
        cartService.deleteAllCart(userId);
    }

    // 제품 등록
    @PostMapping("/addProduct/{userId}")
    public void addProduct(@PathVariable Long userId, @RequestBody RequestAddProduct requestAddProduct) {
        productService.addProduct(userId, requestAddProduct);
    }


}
