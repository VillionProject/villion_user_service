package com.example.villion_user_service.controller;

import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.request.RequestLogin;
import com.example.villion_user_service.domain.request.RequestUser;
import com.example.villion_user_service.domain.response.ResponseLogin;
import com.example.villion_user_service.domain.response.ResponseUser;
import com.example.villion_user_service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseLogin> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(user, UserDto.class); // RequestUser 객체를 UserDto로 전달

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
    public ResponseEntity<ResponseUser> findByID(@PathVariable("userId") Long id) {
        Optional<UserEntity> userEntity = userService.findByUserId(id);

//        TODO ResponseUser로 형변환이 안되고 있음
        ResponseUser returnValue = new ModelMapper().map(userEntity, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

}
