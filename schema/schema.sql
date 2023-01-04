CREATE TABLE IF NOT EXISTS Events
(
    id              BIGINT PRIMARY KEY,
    event_type      varchar(255),
    payload         json,
    payload_version varchar(20),
    occurred_at     TIMESTAMPTZ not null,
    published_at    TIMESTAMPTZ DEFAULT NOW(),
    version         INT         not null
);


CREATE TABLE IF NOT EXISTS pulled_offset
(
    id               BIGINT PRIMARY KEY,
    current_position TIMESTAMPTZ not null,
    triggered_at     TIMESTAMPTZ not null,
    pipeline_id      BIGINT      not null,
    destination_id   BIGINT      not null,
    status           VARCHAR(30),
    created_at       TIMESTAMPTZ DEFAULT NOW(),
    version          INT         not null
);

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
