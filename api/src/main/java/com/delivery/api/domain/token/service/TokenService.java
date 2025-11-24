package com.delivery.api.domain.token.service;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.helper.TokenHelperInterface;
import com.delivery.api.domain.token.model.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * token에 대한 도메인 로직 처리
 * */
@RequiredArgsConstructor
@Service
public class TokenService {
    
    private final TokenHelperInterface tokenHelperInterface;
    
    // accessToken 발급
    public TokenDto issueAccessToken(Long userId){
        // 내부에서 토큰 발행할 때 userId 사용할 예정
        // userId 사용해서 payload data 만들어서 토큰 발급
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperInterface.issueAccessToken(data);
    }
    
    // refreshToken 발급
    public TokenDto issueRefreshToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperInterface.issueRefreshToken(data);
    }
    
    // token validation : 해당 토큰 데이터 받아서 userId 넘겨줌
    public Long validationToken(String token){
        var map = tokenHelperInterface.validationTokenWithThrow(token);
        
        var userId = map.get("userId"); // 토큰에서 userId 값을 꺼내와서

        Objects.requireNonNull(userId, ()->{throw new ApiException(ErrorCode.NULL_POINT); });
        
        return Long.parseLong(userId.toString()); // userId 리턴
    }
    
}
