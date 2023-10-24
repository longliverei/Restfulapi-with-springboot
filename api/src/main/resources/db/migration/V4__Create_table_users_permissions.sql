CREATE TABLE IF NOT EXISTS users_permissions (
    id_user bigint NOT NULL references users(id),
    id_permission bigint NOT NULL references permissions(id),
    PRIMARY KEY (id_user, id_permission)
)