CREATE TABLE IF NOT EXISTS destination
(
    id      BIGINT PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    type    VARCHAR(50)  NOT NULL,
    status  VARCHAR(50),
    version BIGINT       NOT NULL
);

CREATE TABLE IF NOT EXISTS relay_message
(
    id              BIGINT PRIMARY KEY,
    destination_id  BIGINT NOT NULL,
    message_payload json,
    created_at      TIMESTAMPTZ,
    relied_at       TIMESTAMPTZ,
    payload_version VARCHAR(50),
    version         INT    NOT NULL
);
