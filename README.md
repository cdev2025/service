# service

erDiagram
USER {
BIGINT id PK "NOT NULL, AUTO_INCREMENT"
VARCHAR_50 name "NOT NULL"
VARCHAR_100 email "NOT NULL"
VARCHAR_100 password "NOT NULL"
VARCHAR_50 status "NOT NULL"
VARCHAR_150 address "NOT NULL"
DATETIME registered_at
DATETIME unregistered_at
DATETIME last_login_at
}

    STORE {
        BIGINT id PK "NOT NULL, AUTO_INCREMENT"
        VARCHAR_100 name "NOT NULL"
        VARCHAR_150 address "NOT NULL"
        VARCHAR_50 status "NOT NULL"
        VARCHAR_50 category "NOT NULL"
        DOUBLE star "DEFAULT 0"
        VARCHAR_200 thumbnail_url "NOT NULL"
        DECIMAL_11_4 minimum_amount "NOT NULL"
        DECIMAL_11_4 minimum_delivery_amount "NOT NULL"
        VARCHAR_20 phone_number
    }

    STORE_MENU {
        BIGINT id PK "NOT NULL, AUTO_INCREMENT"
        BIGINT store_id "NOT NULL"
        VARCHAR_100 name
        DECIMAL_11_4 amount "NOT NULL"
        VARCHAR_50 status "NOT NULL"
        VARCHAR_200 thumbnail_url "NOT NULL"
        INT like_count "DEFAULT 0"
        INT sequence "DEFAULT 0"
    }

    STORE ||--o{ STORE_MENU : "store_id"
