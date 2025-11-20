package com.delivery.api.common.exceptionhandler;

import com.delivery.api.common.api.Api;
import com.delivery.api.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE) // 최우선 처리
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class) // ApiException 클래스로 발생하는 모든 예외를 다 캐치
    public ResponseEntity<Api<Object>> apiException(
            ApiException apiException
    ){
        log.error("", apiException); //stackTrace : ApiException도 RuntimeException 상속 받아서 stackTrace 가능

        // response 커스텀하게 보낼 수 있음
        var errorCode = apiException.getErrorCodeInterface();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, apiException.getErrorDescription())
                );
    }
}
