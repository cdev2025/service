package com.delivery.api.domain.token.converter;

import com.delivery.api.common.annotation.Converter;
import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.token.model.TokenDto;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Converter
public class TokenConverter {

    // TokenDto -> TokenResponse로 변환
    public TokenResponse tokenResponse(
            TokenDto accessToken,
            TokenDto refreshToken
    ){
        // Null값 체크: 둘 다 null이면 예외가 발생하도록 accessToken, refreshToken 모두 체크
        Objects.requireNonNull(accessToken, () -> {throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken, () -> {throw new ApiException(ErrorCode.NULL_POINT);});

        // 위에서 예외처리 했으므로, response는 항상 정상적인 것만 리턴
        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiresAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiresAt())
                .build();
    }
}
