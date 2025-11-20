package com.delivery.api.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
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

        return true; // 일단 통과 처리
    }
}
