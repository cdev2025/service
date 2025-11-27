package com.delivery.api.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderRequest {

    // 주문
    // 특정 사용자(로그인된)가 특정 메뉼르 주문
    // 1. 로그인된 세션에 들어있는 사용자
    // 2. 특정 메뉴 id 리스트

    private List<Long> storeMenuIdList;
}
