package com.example.villion_user_service.controller;

import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.request.RequestLogin;
import com.example.villion_user_service.domain.request.RequestUser;
import com.example.villion_user_service.domain.response.ResponseUser;
import com.example.villion_user_service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("signup")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(user, UserDto.class); // RequestUser 객체를 UserDto로 전달

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }



    // 로그인
//    @PostMapping("/login")
//    public void login (RequestLogin requestLogin) {
//
//
//    }



    // 내정보 조회



}
