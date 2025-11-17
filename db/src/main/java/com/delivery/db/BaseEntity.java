package com.delivery.db;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@MappedSuperclass // BaseEntity라 따로 매핑 안하고, 상속에만 사용
@Data
@SuperBuilder // 부모로 부터 상속 받은 변수도 빌더패턴에 포함시키겠다라는 옵셥
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql
    private Long id;
}
