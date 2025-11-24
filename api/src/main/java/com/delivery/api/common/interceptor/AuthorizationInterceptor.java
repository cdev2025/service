package com.delivery.api.common.interceptor;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.error.TokenErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.token.business.TokenBusiness;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        // WEB 유저(특히 크롬)의 경우 GET, POST 등 API요청 전에 Option이라는 API를 요청해서 해당 메서드 지원하는지 체크하는 API가 존재
        // WEB, chrome의 경우 GET< POST OPTIONS = pass
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        // 리소스: api 요청이 아니라 JavaScript/HTML 받아가는 요청. 이미지 받아가는 요청 => 통과
        // js, html, png resource를 요청하는 경우 = pass
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        // 나중에 여기에 인증 넣을 예정
        // TODO : header 검증
        var accessToken = request.getHeader("authorization-token");
        if(accessToken == null){
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        // token validation
        var userId = tokenBusiness.validationAccessToken(accessToken);

        if(userId != null){
            // context에 저장:
            // [context] local thread로 한 가지 요청(한가지 request)에 대해서 유효하게 global하게 저장할 수 있는 영역(저장소)
            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);

            return true; //인증 통과
        }

        throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패");
    }
}
