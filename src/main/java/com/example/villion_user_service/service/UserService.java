package com.example.villion_user_service.service;

import com.example.villion_user_service.client.ProductServiceClient;
import com.example.villion_user_service.common.RestError;
import com.example.villion_user_service.common.RestResult;
import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.entity.WishLibraryEntity;
import com.example.villion_user_service.domain.entity.WishProductFolderEntity;
import com.example.villion_user_service.domain.eunm.Grade;
import com.example.villion_user_service.domain.eunm.LibraryStatus;
import com.example.villion_user_service.domain.request.RequestAddFolder;
import com.example.villion_user_service.domain.request.RequestAddFolderProduct;
import com.example.villion_user_service.domain.request.RequestLogin;
import com.example.villion_user_service.domain.request.RequestUser;
import com.example.villion_user_service.domain.response.ResponseLogin;
import com.example.villion_user_service.domain.response.ResponseProducts;
import com.example.villion_user_service.kafka.GetProductsByLocationProducer;
import com.example.villion_user_service.kafka.TopicConfig;
import com.example.villion_user_service.repository.UserRepository;
import com.example.villion_user_service.repository.WishLibraryRepository;
import com.example.villion_user_service.repository.WishProductFolderRepository;
import com.example.villion_user_service.repository.WishProductRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final WishLibraryRepository wishLibraryRepository;
    private final WishProductRepository wishProductRepository;
    private final WishProductFolderRepository wishProductFolderRepository;
    private final GetProductsByLocationProducer getProductsByLocationProducer;
    private final ProductServiceClient productServiceClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<RestResult<Object>> createUser(UserDto userDto) {
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
                .baseLocationId("지역 미지정")
//                .interstCategory(List.of(Category.NOT_SPECIFIED))
                .build();

        if (userRepository.findByEmail(userDto.getEmail()) != null ) {
            // 이메일이 이미 존재하는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RestResult<>("error", new RestError("DUPLICATE", "이미 가입된 아이디입니다.")));
        }


//        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);


        userRepository.save(userEntity);
        return ResponseEntity.ok(new RestResult<>("success", "회원가입되었습니다."));
    }



    public ResponseEntity<RestResult<Object>> login(RequestLogin requestLogin) {
        UserEntity byEmail = userRepository.findByEmail(requestLogin.getEmail());

        // user없을 경우
        if (byEmail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RestResult<>("error", new RestError("NOT_FOUND", "아이디가 존재하지 않습니다.")));
        }

        // email 불일치
        if (!requestLogin.getEmail().equals(byEmail.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RestResult<>("error",new RestError("EMAIL_MISMATCH", "이메일을 다시 입력해주세요.")));
        }


        // password 불일치
        if (!bCryptPasswordEncoder.matches(requestLogin.getPassword(), byEmail.getPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RestResult<>("error",new RestError("PASSWORD_MISMATCH", "비밀번호를 다시 입력해주세요.")));
        }


        ResponseLogin responseLogin = ResponseLogin.builder()
                .email(byEmail.getEmail())
                .libraryName(byEmail.getLibraryName())
                .phoneNumber(byEmail.getPhoneNumber())
                .libraryStatus(byEmail.getLibraryStatus())
                .createdAt(byEmail.getCreatedAt())
                .grade(byEmail.getGrade())
                .profileImage(byEmail.getProfileImage())
                .familyAccount(byEmail.getFamilyAccount())
                .interestCategory(byEmail.getInterestCategory())
                .baseLocationId(byEmail.getBaseLocationId())
                .isLogin(true)
                .userId(byEmail.getUserId())
                .build();


        return ResponseEntity.ok(new RestResult<>("success", responseLogin));
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
            userEntity.setBaseLocationId(requestUser.getBase_location_id());
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



    public void toggleWishLibrary(Long userId, Long wishLibraryId) {
        WishLibraryEntity byUserIdAndWishLibraryId = wishLibraryRepository.findByUserIdAndWishLibraryId(userId, wishLibraryId);

        // 본인은 추가 못함..
        if(userId.equals(wishLibraryId)) {
            throw new IllegalArgumentException("본인의 도서관은 찜 목록에 추가할 수 없습니다.");
        }

        if(byUserIdAndWishLibraryId == null) {
            WishLibraryEntity wishLibraryEntity = WishLibraryEntity.builder()
                    .wishLibraryId(wishLibraryId)
                    .userId(userId)
                    .build();
            wishLibraryRepository.save(wishLibraryEntity);
        } else {
            wishLibraryRepository.delete(byUserIdAndWishLibraryId);
        }



    }

    // 상품 찜하기
    public void toggleWishProduct(Long userId, RequestAddFolderProduct requestAddFolderProduct) {

        WishProductFolderEntity folderEntity = wishProductFolderRepository.findByUserIdAndFolderName(userId, requestAddFolderProduct.getFolderName());

        // 처음 찜하기를 눌렀을 경우, 배열을 만들어야함..
        if(folderEntity.getProducts()==null) {
            // 제품 ID를 저장할 리스트를 생성
            List<Long> productIds = new ArrayList<>();

            // 제품 ID를 리스트에 추가
            productIds.add(requestAddFolderProduct.getProductId());

            // Gson 객체 생성
            Gson gson = new Gson();

            // 리스트를 JSON 형식으로 변환하여 저장
            String productIdsJson = gson.toJson(productIds);
            folderEntity.setProducts(productIdsJson);

            // 저장
            wishProductFolderRepository.save(folderEntity);

        } else {
            // 기존의 제품 ID 문자열을 Gson을 사용하여 배열로 변환
            Gson gson = new Gson();
            Long[] existingProductIds = gson.fromJson(folderEntity.getProducts(), Long[].class);

            // 요청된 제품 ID를 포함하고 있는지 확인
            List<Long> productList = new ArrayList<>(Arrays.asList(existingProductIds));
            Long requestedProductId = requestAddFolderProduct.getProductId();

            if (productList.contains(requestedProductId)) {
                // 제품이 이미 목록에 있으면 제거
                productList.remove(requestedProductId);
            } else {
                // 제품이 목록에 없으면 추가
                productList.add(requestedProductId);
            }

            // 리스트를 다시 JSON 형식의 문자열로 변환하여 저장
            String updatedProductIdsJson = gson.toJson(productList);
            folderEntity.setProducts(updatedProductIdsJson);

            // 변경된 폴더 엔티티를 저장
            wishProductFolderRepository.save(folderEntity);
        }
    }

// 폴더 만들기
    public void addFolderWishProduct(Long userId, RequestAddFolder requestAddFolder) {

        WishProductFolderEntity folderEntity = WishProductFolderEntity.builder()
                .folderName(requestAddFolder.getFolderName())
                .userId(userId)
                .build();

        wishProductFolderRepository.save(folderEntity);
    }


    public List<WishProductFolderEntity> getWishProductFolder(Long userId) {
        // 디폴트로 "기본폴더" 만들어서 보여주기
        // 기본 폴더가 있는지 확인
        WishProductFolderEntity defaultFolder = wishProductFolderRepository.findByUserIdAndFolderName(userId, "기본폴더");
        if(!Objects.equals(defaultFolder.getFolderName(), "기본폴더")) {
            WishProductFolderEntity folderEntity = WishProductFolderEntity.builder()
                    .userId(userId)
                    .folderName("기본폴더")
                    .build();

            wishProductFolderRepository.save(folderEntity);
        }


        return wishProductFolderRepository.findAllByUserId(userId);
    }

    public List<WishProductFolderEntity> wishProductFolderDetail(Long userId, String folderName) {
        return null;
    }


    public List<ResponseProducts> getProductsByLocation(Long userId) {
        UserEntity byUserId = userRepository.findByUserId(userId);
        getProductsByLocationProducer.send(TopicConfig.getProductsByLocation, byUserId.getBaseLocationId());

        return productServiceClient.getProductsByLocation();
    }


}
