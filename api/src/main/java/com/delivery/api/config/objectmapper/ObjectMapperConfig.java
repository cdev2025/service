package com.delivery.api.config.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 커스텀된 ObjectMapper를 빈으로 등록
@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){
        var objectMapper = new ObjectMapper();

        // 커스텀해서 쓰도록 설정


        return objectMapper();
    }
}
