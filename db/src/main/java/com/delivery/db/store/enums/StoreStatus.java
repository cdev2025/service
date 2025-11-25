package com.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreStatus {

    // 대기 상태 : 등록 전 - 가입 후 관리자 승인 대기 상태

    REGISTERD("등록"),

    // 해지 신청 : 해지 전, 해지 신청 후 관리자 승인 대기 상태

    UNREGISTERD("해지"),

    ;


    private String description;
}
