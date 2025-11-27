package com.delivery.api.domain.userorder.controller;

import com.delivery.api.common.annotation.UserSession;
import com.delivery.api.common.api.Api;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.business.UserOrderBusiness;
import com.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-order")
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;

    // 사용자 주문
    @PostMapping
    public Api<UserOrderResponse> userOrder(
            @Valid
            @RequestBody Api<UserOrderRequest> request,

            @Parameter(hidden = true) // swagger에서 보여지면 안되니까
            @UserSession User user // 저장해 놓은 User 데이터 가져오는 부분
    ){
        var response = userOrderBusiness.userOrder(user, request.getBody());
        return Api.OK(response);
    }

    // 현재 진행중인 주문건
    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> current(
            @Parameter(hidden = true)
            @UserSession User user
    ){
        var response = userOrderBusiness.current(user);
        return Api.OK(response);
    }

    // 과거 주문 내역
    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> history(
            @Parameter(hidden = true)
            @UserSession User user
    ){
        var response = userOrderBusiness.history(user);
        return Api.OK(response);
    }

    // userOrderId로 주문 1건에 대한 조회
    @GetMapping("/id/{orderId}")
    public Api<UserOrderDetailResponse> read(
            @Parameter(hidden = true)
            @UserSession User user,

            @PathVariable Long orderId
    ){
        var response = userOrderBusiness.read(user, orderId);
        return Api.OK(response);
    }
}
