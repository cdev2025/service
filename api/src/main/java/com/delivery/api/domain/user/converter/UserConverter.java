package com.delivery.api.domain.user.converter;

import com.delivery.api.common.annotation.Converter;
import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.user.controller.model.UserRegisterRequest;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.db.user.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserConverter {

    // UserRegisterRequest DTO -> UserEntity로 바꿔주는 메서드
    public UserEntity toEntity(UserRegisterRequest request){
        return Optional.ofNullable(request)
                .map(
                        it -> {
                            // dto to entity
                            return UserEntity.builder()
                                    .name(request.getName())
                                    .email(request.getEmail())
                                    .password(request.getPassword())
                                    .address(request.getAddress())
                                    .build();
                        }
                )
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
                //request가 null값이라면 예외 발생 -> 사용자 정의 ExceptionHandler에서 catch해서 stackTrace 찍고, 오류 response 보냄
    }

    // UserEntity를 받아서 UserResponse DTO로 바꿔주는 역할
    //public Object toResponse(UserEntity savedEntity) {
    public UserResponse toResponse(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map( it -> {
                        // to response
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .email(userEntity.getEmail())
                            .status(userEntity.getStatus())
                            .address(userEntity.getAddress())
                            .registeredAt(userEntity.getRegisteredAt())
                            .unregisteredAt(userEntity.getUnregisteredAt())
                            .lastLoginAt(userEntity.getLastLoginAt())
                            .build();
                        }
                )
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity is Null"));
    }
}
