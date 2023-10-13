CREATE TABLE IF NOT EXISTS users (
    id bigserial NOT NULL,
    username varchar(255) DEFAULT NULL,
    full_name varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    account_non_expired bit(1) DEFAULT NULL,
    account_non_locked bit(1) DEFAULT NULL,
    credentials_non_expired bit(1) DEFAULT NULL,
    enabled bit(1) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE (username)
)