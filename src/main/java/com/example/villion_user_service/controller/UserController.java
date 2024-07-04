package com.example.villion_user_service.controller;

import com.example.villion_user_service.domain.dto.UserDto;
import com.example.villion_user_service.domain.entity.CartEntity;
import com.example.villion_user_service.domain.entity.UserEntity;
import com.example.villion_user_service.domain.entity.WishProductFolderEntity;
import com.example.villion_user_service.domain.eunm.RentalMethod;
import com.example.villion_user_service.domain.request.*;
import com.example.villion_user_service.domain.response.ResponseLogin;
import com.example.villion_user_service.domain.response.ResponseProducts;
import com.example.villion_user_service.domain.response.ResponseUser;
import com.example.villion_user_service.repository.UserRepository;
import com.example.villion_user_service.service.CartService;
import com.example.villion_user_service.service.ProductService;
import com.example.villion_user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;
    private final UserRepository userRepository;

    @PostMapping("/signup2")
    public ResponseEntity<ResponseLogin> createUser2(@RequestBody RequestSignup requestSignup) {

        System.out.println("zzzzzz");
//        ModelMapper mapper = new ModelMapper();
//        UserDto userDto = mapper.map(requestSignup, UserDto.class); // RequestLogin 객체를 UserDto로 전달
//
//        ResponseLogin responseLogin = mapper.map(userDto, ResponseLogin.class);
//
//        userService.createUser(userDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseLogin);
        return null;
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


    // 찜 도서관 목록에 넣기/빼기
    // 찜하려는 도서관ID가 목록에 없으면, 추가 // 있으면, 제거
    @PostMapping("/{userId}/wishLibrary/toggle")
    public void toggleWishLibrary(@PathVariable Long userId, @RequestParam Long wishLibraryId) {
        userService.toggleWishLibrary(userId, wishLibraryId);
    }



    // 찜 도서 폴더 만들기
    @PostMapping("/{userId}/wishProduct/folder")
    public void addFolderWishProduct(@PathVariable Long userId, @RequestBody RequestAddFolder requestAddFolder) {
        userService.addFolderWishProduct(userId, requestAddFolder);
    }


    // 찜 도서 목록에 넣기/빼기
    @PostMapping("/{userId}/wishProduct/toggle")
    public void toggleWishProduct(@PathVariable Long userId, @RequestBody RequestAddFolderProduct requestAddFolderProduct) {
        userService.toggleWishProduct(userId, requestAddFolderProduct);
    }


    // 폴더 목록 보기
    @GetMapping("/{userId}/wishProductFolder")
    public List<WishProductFolderEntity> getWishProductFolder(@PathVariable Long userId) {
        return userService.getWishProductFolder(userId);
    }


    // 찜도서 폴더 상세 보기
    @GetMapping("/{userId}/wishProductFolder/Detail")
    public List<WishProductFolderEntity> wishProductFolderDetail(@PathVariable Long userId, @RequestParam String folderName) {
        return userService.wishProductFolderDetail(userId, folderName);
    }


    // 장바구니 담기
    @PostMapping("/addCart")
    public void addCart(@RequestBody RequestCart requestCart) {
        cartService.addCart(requestCart);

    }

    // 장바구니 보여주기
//    @GetMapping("/getCart/{userId}")
//    public Map<Long, CartEntity> getCart(@PathVariable("userId") Long userId) {
//        Map<Long, CartEntity> cart = cartService.getCart(userId);
//        return cart;
//    }

    // (장바구니에서) 직거래 보기 - "대여" 보여주기
    @GetMapping("/getFaceRentalCart/{userId}")
    public Map<Long, CartEntity> getFaceRentalCart(@PathVariable("userId") Long userId) {
        Map<Long, CartEntity> cart = cartService.getCart(userId);

        // rentable이 true인 항목만 필터링하여 새로운 맵에 수집
        Map<Long, CartEntity> filteredCart = cart.entrySet().stream()
                .filter(entry -> entry.getValue().getRentalMethod().equals(RentalMethod.FACE_TO_FACE)) // 직거래
                .filter(entry -> entry.getValue().getRentable()) // 대여가능
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return filteredCart;
    }

    // (장바구니에서) 직거래 보기 - "구매" 보여주기
    @GetMapping("/getFacePurchaseCart/{userId}")
    public Map<Long, CartEntity> getFacePurchaseCart(@PathVariable("userId") Long userId) {
        Map<Long, CartEntity> cart = cartService.getCart(userId);

        // rentable이 true인 항목만 필터링하여 새로운 맵에 수집
        Map<Long, CartEntity> filteredCart = cart.entrySet().stream()
                .filter(entry -> entry.getValue().getRentalMethod().equals(RentalMethod.FACE_TO_FACE)) // 직거래
                .filter(entry -> entry.getValue().getPurchasable()) // 구매가능
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return filteredCart;
    }




//     (장바구니에서) 직배송(빌런배송) 보기 - "대여" 보여주기
    @GetMapping("/getDeliveryRentalCart/{userId}")
    public Map<Long, CartEntity> getDeliveryRentalCart(@PathVariable("userId") Long userId) {
        Map<Long, CartEntity> cart = cartService.getCart(userId);

        // rentable이 true인 항목만 필터링하여 새로운 맵에 수집
        Map<Long, CartEntity> filteredCart = cart.entrySet().stream()
                .filter(entry -> entry.getValue().getRentalMethod().equals(RentalMethod.DELIVERY)) // 직배송
                .filter(entry -> entry.getValue().getRentable()) // 대여가능
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return filteredCart;
    }

    //     (장바구니에서) 직배송(빌런배송) 보기 - "구매" 보여주기
    @GetMapping("/getDeliveryPurchaseCart/{userId}")
    public Map<Long, CartEntity> getDeliveryPurchaseCart(@PathVariable("userId") Long userId) {
        Map<Long, CartEntity> cart = cartService.getCart(userId);

        // rentable이 true인 항목만 필터링하여 새로운 맵에 수집
        Map<Long, CartEntity> filteredCart = cart.entrySet().stream()
                .filter(entry -> entry.getValue().getRentalMethod().equals(RentalMethod.DELIVERY)) // 직배송
                .filter(entry -> entry.getValue().getPurchasable()) // 구매가능
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return filteredCart;
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


    // TODO 등록된 제품 보기(메인화면) + 다른 곳에 문제 있어도 제품은 볼 수 있어야 함..(분산처리)
    @GetMapping("/getProductsByLocation/{userId}")
    public List<ResponseProducts> getProductsByLocation(@PathVariable Long userId) {
        return userService.getProductsByLocation(userId);
    }



    // product-service에 openFeign으로 도서관이름 보내주기
    @GetMapping("/getGetLibraryWithProduct/{userId}")
    public String getGetLibraryWithProduct(@PathVariable Long userId) {
        Optional<UserEntity> byUserId = userService.findByUserId(userId);
        return byUserId.get().getLibraryName();
    }



    // TODO 이 책과 함께 주문된 도서


    // TODO 이 시리즈의 다른 책



    // TODO 직거래 신청(1:1 채팅으로 넘어가기)




    // 직배송 신청(일반적인 구매로직)
    @PostMapping("/addDeliveryOrder/{userId}")
    public void addDeliveryOrder(@PathVariable Long userId, @RequestBody RequestAddDeliveryOrder requestAddDeliveryOrder) {
        productService.addDeliveryOrder(userId, requestAddDeliveryOrder);
    }



}
