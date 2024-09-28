CREATE TABLE token
(
    id        BIGINT  NOT NULL,
    value     VARCHAR(255),
    startTime TIMESTAMP WITHOUT TIME ZONE,
    endTime   TIMESTAMP WITHOUT TIME ZONE,
    isRevoked BOOLEAN NOT NULL,
    revokedBy VARCHAR(255),
    user_id   BIGINT,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);