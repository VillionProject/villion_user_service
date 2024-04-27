package com.example.villion_user_service.service;

import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.eunm.Grade;
import com.example.villion_user_service.domain.eunm.LibraryStatus;
import com.example.villion_user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto) {
// ✔ UserDto -> UserEntity 변환 작업(ModelMapper 사용)
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 매칭전략을 강력하게(딱 맞아떨어지지 않으면 지정할수 없도록) 설정

//        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        UserEntity userEntity = UserEntity.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .libraryName(userDto.getLibraryName())
                .grade(Grade.NORMAL)
                .createdAt(LocalDate.now())
                .libraryStatus(LibraryStatus.CLOSED)
                .profileImage("test")
                .phoneNumber(Long.valueOf("1234"))
                .familyAccount("test")
                .yearlyReadingTarget(0)
                .build();

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }


        // return : 로그인이 모두 통과되었을 때 진행, new ArrayList<>() : 권한리스트
        // User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)
        return new User(userEntity.getEmail(), userEntity.getPassword(), true, true,true,true, new ArrayList<>());
    }

    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;

    }


    public Optional<UserEntity> findByUserId(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return userEntity;
    }
}
