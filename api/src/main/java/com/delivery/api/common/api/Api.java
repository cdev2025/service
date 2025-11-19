package com.delivery.api.common.api;

import com.delivery.api.common.error.ErrorCodeInterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T body; // body 부분 제너릭 타입으로 선언

    public static <T> Api<T> OK(T data){
        var api = new Api<T>();

        api.result = Result.OK();
        api.body = data;

        return api;
    }

    // 에러가 나면 body에 세팅할 내용이 없기 때문에 제너릭 경고를 없애기 위해 오브젝트로 세팅하고 body내용 없애고
    public static Api<Object> ERROR(Result result){ // 바깥에서 result 만들어서 넣어주는 방법
        var api = new Api<Object>();
        api.result = result;
        return api;
    }

    // 에러 코드를 받아오는 경우 : 간단하게 에러코드만 넘기면 바로 처리
    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface);
        return api;
    }

    // 에러 코드 인터페이스와 스로워블 최상위 예외를 받아서 이것도 같이 넘겨주는방법
    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface, Throwable tx){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface, tx);
        return api;
    }

    // 메시지를 같이 넘기는 경우
    public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface, String description){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeInterface, description);
        return api;
    }
}
