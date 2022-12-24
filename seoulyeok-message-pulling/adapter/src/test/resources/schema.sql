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
