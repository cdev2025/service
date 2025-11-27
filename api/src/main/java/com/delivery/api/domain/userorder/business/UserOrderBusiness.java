package com.delivery.api.domain.userorder.business;

import com.delivery.api.common.annotation.Business;
import com.delivery.api.domain.store.converter.StoreConverter;
import com.delivery.api.domain.store.service.StoreService;
import com.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.api.domain.storemenu.service.StoreMenuService;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import com.delivery.api.domain.userorder.converter.UserOrderConverter;
import com.delivery.api.domain.userorder.service.UserOrderService;
import com.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import com.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    
    private final UserOrderConverter userOrderConverter;

    private final UserOrderMenuConverter userOrderMenuConverter;

    private final UserOrderMenuService userOrderMenuService;

    private final StoreService storeService;
    private final StoreConverter storeConverter;

    // [ 사용자 주문 로직 ]
    // 1. 사용자, 메뉴 id 받아오기
    // 2. UserOrder 생성 (사용자 주문 만들어 주고)
    // 3. userOrderMenu 생성 (사용자 주문과 메뉴를 맵핑 시켜주는 테이블)
    // 4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequest request) {
        var storeMenuEntityList = request.getStoreMenuIdList()
                .stream()
                .map(storeMenuService::getStoreMenuWithThrow)
                .toList();
        
        var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);
        
        // 주문 : UserOrder 생성
        var savedUserOrderEntity = userOrderService.order(userOrderEntity);
        
        // 매핑 : UserOrderMenu 생성 -> 메뉴별로 매핑
        var userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> {
                    // user order + menu
                    var userOrderMenuEntity = userOrderMenuConverter.toEntity(
                            savedUserOrderEntity,
                            it
                    );

                    return userOrderMenuEntity;
                })
                .toList();  //.collect(Collectors.toList());

        // 주문 내역 기록으로 남기기
        userOrderMenuEntityList.forEach(userOrderMenuService::order);
//        userOrderMenuEntityList.forEach(it -> {
//            userOrderMenuService.order(it);
//        });

        // to Response
        return userOrderConverter.toResponse(savedUserOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {
        // 현재 사용자의 주문내역 가져오기
        var userOrderEntityList = userOrderService.current(user.getId());

        // 주문 1건씩 처리(주문 내역 당 메뉴 정보, 스토어 정보 가져오기)
        var userOrderDeatilResponseList = userOrderEntityList.stream().map(it -> {
            // 사용자 주문 메뉴
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());

            var storeMenuEntityList = userOrderMenuEntityList.stream()
                    .map(userOrderMenuEntity ->{
                      var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                      return storeMenuEntity;
                    })
                    .toList();

            // 사용자가 주문한 스토어 : TODO 리팩토링 필요
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(it))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).toList();

        return userOrderDeatilResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {
        // 과거 사용자의 주문내역 가져오기
        var userOrderEntityList = userOrderService.history(user.getId());

        // 주문 1건씩 처리(주문 내역 당 메뉴 정보, 스토어 정보 가져오기)
        var userOrderDeatilResponseList = userOrderEntityList.stream().map(it -> {
            // 사용자 주문 메뉴
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());

            var storeMenuEntityList = userOrderMenuEntityList.stream()
                    .map(userOrderMenuEntity ->{
                        var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                        return storeMenuEntity;
                    })
                    .toList();

            // 사용자가 주문한 스토어 : TODO 리팩토링 필요
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(it))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();
        }).toList();

        return userOrderDeatilResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity = userOrderService.getUserOrderWithoutStatusWithThrow(orderId, user.getId());

        // 사용자 주문 메뉴
        var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

        var storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrderMenuEntity ->{
                    var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                })
                .toList();

        // 사용자가 주문한 스토어 : TODO 리팩토링 필요
        var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }
}
