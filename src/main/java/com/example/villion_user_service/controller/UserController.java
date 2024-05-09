package com.example.villion_user_service.controller;

import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.entity.ProductEntity;
import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.request.RequestSignup;
import com.example.villion_user_service.domain.request.RequestUser;
import com.example.villion_user_service.domain.response.ResponseLogin;
import com.example.villion_user_service.domain.response.ResponseUser;
import com.example.villion_user_service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    @PostMapping("/addCart/{productId}")
    public void addCart(@PathVariable List<Long> productId) {
        List<Long> productList = new ArrayList<>();
        for(Long id : productId) {
            productList.add(id);
        }


//        productId.forEach(v -> productList.add(ProductEntity));
    }

    // 장바구니 비우기
    @PostMapping("/removeCart/{productId}")
    public void removeCart(@PathVariable List<Long> productId) {

    }
}
