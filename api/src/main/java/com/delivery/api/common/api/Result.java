package com.delivery.api.common.api;

import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.error.ErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK(){
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();
    }

    public static Result ERROR(ErrorCodeInterface errorCodeInterface){
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription("오류")
                .build();
    }

    public static Result ERROR(ErrorCodeInterface errorCodeInterface,  Throwable tx){
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription(tx.getLocalizedMessage()) // 추천하지는 않음. getLocalizedMessage 를 통해 실제 어떤 메서드가 쓰였는지 확인용
                .build();
    }

    // 특정 에러 코드와 메시지를 넘기면, Result에 담아서 response로 내려갈 수 있도록 설정
    public static Result ERROR(ErrorCodeInterface errorCodeInterface, String descriptions){
        return Result.builder()
                .resultCode(errorCodeInterface.getErrorCode())
                .resultMessage(errorCodeInterface.getDescription())
                .resultDescription(descriptions)
                .build();
    }
}
