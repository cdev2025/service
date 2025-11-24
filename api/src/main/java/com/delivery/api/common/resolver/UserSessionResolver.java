package com.delivery.api.common.resolver;

import com.delivery.api.common.annotation.UserSession;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크

        // 1. 어노테이션이 있는지 체크
        var annotaion = parameter.hasParameterAnnotation(UserSession.class);

        // 2. 파라미터 타입 체크
        var parameterType = parameter.getParameterType().equals(User.class);

        // 둘 다 true 일 때만 resolerArgument로 넘어가도록 함.
        return (annotaion && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // supportParameter를 통과하면 그 다음에 여기로 옴
        // support parameter에서 true 반환시 실행

        // request context holder에서 정보 찾아와서
        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        var userEntity = userService.getUserWithThrow(Long.parseLong(String.valueOf(userId)));

        // 사용자 정보 세팅 리턴
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .status(userEntity.getStatus())
                .address(userEntity.getAddress())
                .registeredAt(userEntity.getRegisteredAt())
                .unregisteredAt(userEntity.getUnregisteredAt())
                .lastLoginAt(userEntity.getLastLoginAt())
                .build();
    }
}
