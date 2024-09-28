CREATE TABLE user_role
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id        BIGINT  NOT NULL,
    firstName VARCHAR(255),
    lastName  VARCHAR(255),
    email     VARCHAR(255),
    password  VARCHAR(255),
    isActive  BOOLEAN NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES users (id);