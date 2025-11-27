package com.delivery.api.domain.userorder.converter;

import com.delivery.api.common.annotation.Converter;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;

@Converter
public class UserOrderConverter {
    // 우리가 필요한건 UserOrderEntity이고
    // 현재 우리가 가지고 있는 정보는 User, StoreMenuEntity 리스트

    public UserOrderEntity toEntity(
            User user, // -> userId
            List<StoreMenuEntity> storeMenuEntityList  // -> amount : 리스트에 있는 menu 가격  합계
    ){
        var totalAmount = storeMenuEntityList.stream()
                .map(StoreMenuEntity::getAmount) // rkr storeMenuEntity의 amount라는 금액 전부 추출
                .reduce(BigDecimal.ZERO, BigDecimal::add); // reduce: 데이터 축소 : BigDecimal::ZERO 는 합산을 시작할 초기값

         return UserOrderEntity.builder()
                 .userId(user.getId())
                 .amount(totalAmount)
                 .build();
    }

    public UserOrderResponse toResponse(
            UserOrderEntity entity)
    {
        return UserOrderResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .status(entity.getStatus())
                .amount(entity.getAmount())
                .orderedAt(entity.getOrderedAt())
                .acceptedAt(entity.getAcceptedAt())
                .cookingStartedAt(entity.getCookingStartedAt())
                .deliveryStartedAt(entity.getDeliveryStartedAt())
                .receivedAt(entity.getReceivedAt())
                .build();
    }
}
