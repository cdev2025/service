package com.delivery.api.domain.user.controller;

import com.delivery.api.common.api.Api;
import com.delivery.api.domain.user.business.UserBusiness;
import com.delivery.api.domain.user.controller.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(){
        var response = userBusiness.me(null);
        return Api.OK(response);
    }
}
