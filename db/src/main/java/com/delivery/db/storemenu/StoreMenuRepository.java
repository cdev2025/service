package com.delivery.db.storemenu;

import com.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreMenuRepository extends JpaRepository<StoreMenuEntity, Long> {

    // 유효한 메뉴 체크 -> id 값으로 1개 가져오기
    // SELECT * FROM store_menu WHERE id = ?  and status = ? ORDER BY id DESC LIMIT 1
    Optional<StoreMenuEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreMenuStatus status);

    // 특정 가게 메뉴 모두 가져오기
    // SELECT * FROM store_menu WHERE store_id = ? and status = ? ORDER BY sequence DESC;
    List<StoreMenuEntity> findAllByStoreIdAndStatusOrderBySequenceDesc(Long storeId, StoreMenuStatus status);

}
