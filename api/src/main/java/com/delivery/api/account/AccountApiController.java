package com.delivery.api.account;

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
        return Api.OK(response);
    }
}
