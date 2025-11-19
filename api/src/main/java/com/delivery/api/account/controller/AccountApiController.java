package com.delivery.api.account.controller;

import com.delivery.api.account.model.AccountMeResponse;
import com.delivery.api.common.api.Api;
import com.delivery.db.account.AccountEntity;
import com.delivery.db.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountRepository accountRepository;

    // http://localhost:8080/api/account
    @GetMapping("")
    public void save(){
        var account = AccountEntity.builder().build();
        accountRepository.save(account);
    }

    // http://localhost:8080/api/account/me
    @GetMapping("me")
    public Api<AccountMeResponse> me(){
        var response = AccountMeResponse.builder()
                .name("홍길동")
                .email("hong@gmail.com")
                .registeredAt(LocalDateTime.now())
                .build();

//        var str = "안녕하세요";
//        //예외 발생 예상되는 코드를 try~catch로 감싸는데
//        try{
//            Integer.parseInt(str);
//        }catch (Exception e){
//            // catch 처리 하는게 아니라
//            // 사용자 정의 예외 발생 시킴
//            throw new ApiException(ErrorCode.SERVER_ERROR, e, "사용자 Me 호출시 에러 발생");
//        }

        return Api.OK(response);
    }
}
