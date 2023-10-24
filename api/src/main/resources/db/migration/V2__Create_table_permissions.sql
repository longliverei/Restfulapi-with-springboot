CREATE TABLE IF NOT EXISTS permissions (
    id bigserial NOT NULL,
    description varchar(200) DEFAULT NULL,
    PRIMARY KEY (id)
)