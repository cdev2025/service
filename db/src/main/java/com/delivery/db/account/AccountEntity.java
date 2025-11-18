package com.delivery.db.account;

import com.delivery.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true) // 객체 비교할 때 사용(부모에 있는 값까지 포함해서 비교 여부)
@Entity
@Table(name="account")
public class AccountEntity extends BaseEntity {
}
