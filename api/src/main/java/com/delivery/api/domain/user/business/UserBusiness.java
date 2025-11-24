package com.delivery.api.domain.user.business;

import com.delivery.api.common.annotation.Business;
import com.delivery.api.common.annotation.UserSession;
import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.business.TokenBusiness;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.user.controller.model.UserLoginRequest;
import com.delivery.api.domain.user.controller.model.UserRegisterRequest;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.api.domain.user.converter.UserConverter;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;

    private  final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;

    /**
     * 사용자에 대한 가입 처리 로직
     * 1. request dto -> entity
     * 2. entity -> save
     * 3. save Entity -> response dto
     * 4. response dto return
     * */
    // public Object register(@Valid UserRegisterRequest body) {
    public UserResponse register(@Valid UserRegisterRequest request) {

        // 1. request dto -> entity
        var entity = userConverter.toEntity(request);

        // 2. entity -> save
        var savedEntity = userService.register(entity);

        // 3. save entity -> response dto
        var response = userConverter.toResponse(savedEntity);

        // 4. response dto return
        return response;

/*
        // java 8 버전 이상에서 함수형으로 코딩 가능
        return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request is null"));

 */
    }

    /**
     * 1. email, password를 가지고 사용자 체크
     * 2. user entity로 로그인 확인
     * 3. token 생성 => 아직 토큰은 적용 안해서, 일단 코드로 작성
     * 4. token response
     * */
    // public Object login(@Valid UserLoginRequest body) {
    public TokenResponse login(UserLoginRequest request){
        // 1. email, password를 가지고 사용자 체크
        var userEntity = userService.login(request.getEmail(), request.getPassword()); // 사용자가 없으면 알아서 throw

        // TODO: 토큰 생성 로직으로 나중에 변경 예정
        var tokenResponse = tokenBusiness.issueToken(userEntity);

        return tokenResponse;
    }

    public UserResponse me(Long userId) {
        var userEntity = userService.getUserWithThrow(userId);
        var response = userConverter.toResponse(userEntity);
        return response;
    }
}
