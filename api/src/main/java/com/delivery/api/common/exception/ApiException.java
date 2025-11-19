package com.delivery.api.common.exception;

import com.delivery.api.common.error.ErrorCodeInterface;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionInterface{

    private final ErrorCodeInterface errorCodeInterface;

    private final String errorDescription;


    public ApiException(ErrorCodeInterface errorCodeInterface){
        // 제일 먼저 부모에게 description 통해서 메시지 설정(RuntimeException에 예외 메시지 전달)
        super(errorCodeInterface.getDescription());
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorCodeInterface.getDescription();;
    }

    // 에러 코드 인터페이시의 description이 아니라 내가 정의한 메시지르 지정하는 경우도 가능
    public ApiException(ErrorCodeInterface errorCodeInterface, String errorDescription){
        super(errorDescription);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorDescription;
    }

    // 또는 예외가 터질 수도 있으니 에러코드와 예외를 전달할 수도 있음
    public ApiException(ErrorCodeInterface errorCodeInterface, Throwable tx){
        // 제일 먼저 부모에게 description 통해서 메시지 설정(RuntimeException에 예외 메시지 전달)
        super(tx); // 부모에게 Throwable(예외) 전달
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorCodeInterface.getDescription();;
    }

    // 모든 것을 다 받는 것도 가능하겠죠?
    public ApiException(ErrorCodeInterface errorCodeInterface, Throwable tx, String errorDescription){
        // 제일 먼저 부모에게 description 통해서 메시지 설정(RuntimeException에 예외 메시지 전달)
        super(tx);
        this.errorCodeInterface = errorCodeInterface;
        this.errorDescription = errorDescription;;
    }
}

