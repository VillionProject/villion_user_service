package com.example.villion_user_service.service;

import com.example.villion_user_service.client.BooksServiceClient;
import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.eunm.Category;
import com.example.villion_user_service.domain.eunm.Grade;
import com.example.villion_user_service.domain.eunm.LibraryStatus;
import com.example.villion_user_service.domain.request.RequestUser;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
                .base_location_id("지역 미지정")
//                .interstCategory(List.of(Category.NOT_SPECIFIED))
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

    public UserEntity updateLibrary (Long id, RequestUser requestUser) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        UserEntity userEntity = userEntityOptional.get();

//        방법 1 : 이게 더 직관적
        if (requestUser.getLibraryName() != null) {
            userEntity.setLibraryName(requestUser.getLibraryName());
        }
        if (requestUser.getLibraryStatus() != null) {
            userEntity.setLibraryStatus(requestUser.getLibraryStatus());
        }
        if (requestUser.getInterstCategory() != null) {
//            userEntity.setInterstCategory(requestUser.getInterstCategory());
        }
        if (requestUser.getYearlyReadingTarget() != 0) {
            userEntity.setYearlyReadingTarget(requestUser.getYearlyReadingTarget());
        }
        if (requestUser.getBase_location_id() != null) {
            userEntity.setBase_location_id(requestUser.getBase_location_id());
        }


        //        방법 2 - for문 사용
//         방법 2 : 반복을 없앨 수 있어서 좋다..
//        Field 클래스는 리플렉션을 통해 클래스의 필드에 동적으로 접근하고 조작할 수 있도록 해주는 중요한 역할
//        for (Field field : RequestUser.class.getDeclaredFields()) {
//            try {
//                Object value = field.get(requestUser);
//
//                // 필드 값이 null이 아니거나 0이 아닌 경우에만 업데이트합니다.
//                if (value != null && !(value instanceof Integer && (Integer)value == 0)) {
//                    Field userField = UserEntity.class.getDeclaredField(field.getName()); // 모든 필드를 가져온다
//                    userField.set(userEntity, value); // 리플렉션을 사용하여 UserEntity 객체의 특정 필드에 value 변수에 저장된 값을 설정
//                }
//            } catch (IllegalAccessException | NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//        }

        userRepository.save(userEntity);

        return userEntity;
    }



    public List<UserEntity> toggleWishLibrary(Long userId, Long wishLibraryId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserEntity byUserId = userRepository.findByUserId(wishLibraryId);

        // 목록에 있으면
        if(userEntity.getWishLibraryList().contains(byUserId)) {
            userEntity.getWishLibraryList().remove(byUserId);
        } else { // 없으면
            userEntity.getWishLibraryList().add(byUserId);
        }

        return userEntity.getWishLibraryList();
    }




}
