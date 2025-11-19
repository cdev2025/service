package com.delivery.api.exceptionhandler;

import com.delivery.api.common.api.Api;
import com.delivery.api.common.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 예외처리를 위한 전역 컨트롤러 설정
@Order(value = Integer.MAX_VALUE) // 가장 마지막에 실행 적용(value값이 낮을 수록 더 높은 우선순위를 가짐)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) // 일어나는 모든 Exception 캐치
    public ResponseEntity<Api<Object>> exception(
            Exception exception
    ){
        log.error("", exception); // 스택 트레이스 확인: 어디서 몇번째 라인에서 에러가 났는지 캐치(우리만 확인 가능)

        return ResponseEntity
                .status(500)
                .body(
                    Api.ERROR(ErrorCode.SERVER_ERROR)
                ); // 사용자에게는 에러 코드만 보냄
    }
}
