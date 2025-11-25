package com.delivery.db.storemenu.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreMenuStatus {

    REGISTERD("등록"),

    UNREGISTERD("해지"),
    ;

    private String description;
}
