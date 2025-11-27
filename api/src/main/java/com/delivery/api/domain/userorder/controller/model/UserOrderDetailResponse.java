package com.delivery.api.domain.userorder.controller.model;

import com.delivery.api.domain.store.controller.model.StoreResponse;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {

    private UserOrderResponse userOrderResponse; // 주문 건에 대한 response 정보

    private StoreResponse storeResponse; // 주문한 가게가 어디인지, store에 대한 정보

    private List<StoreMenuResponse> storeMenuResponseList; // 어떤 메뉴를 주문했는지, menu 정보 리스트
}
