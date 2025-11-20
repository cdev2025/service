package com.delivery.api.domain.user.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.db.user.UserEntity;
import com.delivery.db.user.UserRepository;
import com.delivery.db.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User 도메인 로직을 처리하는 서비스
 * */
@RequiredArgsConstructor
@Service
public class UserService {

    // 서비스는 자신의 도메인 로직만 처리할거라 repository만 정의
    private final UserRepository userRepository;

    // userEntity를 가입 처리(db에 저장)하는 method
    public UserEntity register(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    // 넘어온 userEntity를 가입 처리하기 위한 데이터 setting
                    userEntity.setStatus(UserStatus.REGISTERD);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));
    }
}
