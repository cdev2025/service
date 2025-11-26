package com.delivery.db.userordermenu;

import com.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderMenuRepository extends JpaRepository<UserOrderMenuEntity, Long> {

    // 주문 메뉴에 해당하는 등록된 모든 유효한 메뉴를 리턴하는 메서드
    // SELECT * FROM user_order_menu WHERE user_order_id = ? AND status = ?
    List<UserOrderMenuEntity> findAllByUserOrderIdAndStatus(Long userOrderId, UserOrderMenuStatus status);
}
