package com.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User의 경위 1000번대 에러 코드 사용
 * */
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeInterface{

    USER_NOT_FOUND(400, 1404, "사용자를 찾을 수 없음"),

    // (HttpStatusCode, 우리서비스 에러코드, 코드 설명)
    // HttpStatusCode와 우리 서비스의 에러코드는 같을 수도 있고, 다를 수도 있음
    // 기본적으로 같이 쓰되, 우리쪽에서 에러난 코드는 세분화하도록 합니다.
    ;

    // 변수 선언
    // enum 클래스에 들어가는 값은 변경되지 않아야 하기 때문에 final로 선언
    private final Integer httpStatusCode;

    private final Integer errorCode;

    private final String description;
}
