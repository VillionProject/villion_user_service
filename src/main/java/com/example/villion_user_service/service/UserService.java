package com.example.villion_user_service.service;

import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.eunm.LibraryStatus;
import com.example.villion_user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto) {
// ✔ UserDto -> UserEntity 변환 작업(ModelMapper 사용)
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // 매칭전략을 강력하게(딱 맞아떨어지지 않으면 지정할수 없도록) 설정
        UserEntity userEntity = mapper.map(userDto, UserEntity.class); // UserEntity로 변환
        userEntity.setLibraryStatus(LibraryStatus.CLOSED);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPassword()));

//        userEntity.setEncryptedPwd("encrypted_password"); // 값이 아직 구현이 안됐기 때문에 기본값을 넣어두겠다.
        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }
}
