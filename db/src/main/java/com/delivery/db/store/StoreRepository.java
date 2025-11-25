package com.delivery.db.store;

import com.delivery.db.store.enums.StoreCategory;
import com.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    // id + status 검색
    // SELECT * FROM store WHERE id = ? AND status = ? ORDER BY id DESC LIMIT 1
    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);

    // 유효한 스토어 리스트 : 쿼리는 위와 같은데, 전체 LIST를 가져오는 메서드
    // SELECT * FROM store  status = ? ORDER BY id DESC
    List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

    // 유효한 특정 카테고리의 스토어 리스트 (별점 순으로)
    List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory category);

}
