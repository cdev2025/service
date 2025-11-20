package com.delivery.api.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 클래스, 인터페이스, enum, 어노테이션 선언에 적용 가능
@Retention(RetentionPolicy.RUNTIME) // 실행 중에 적용하기 위해 runtime
@Service
public @interface Business {

    @AliasFor(annotation = Service.class)
    String value() default "";

}
