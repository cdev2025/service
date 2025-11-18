package com.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeInterface{

    OK(200, 200, "성공"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),  500, "서버 에러"),

    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null point"),

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
