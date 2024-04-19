package com.example.villion_user_service.controller;

import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.request.RequestUser;
import com.example.villion_user_service.domain.response.ResponseUser;
import com.example.villion_user_service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String welcome() {
        return "Welcome to the First service";
    }

    // 회원가입
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(user, UserDto.class);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }



    // 로그인


    // 내정보 조회



}
