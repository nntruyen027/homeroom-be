drop table if exists auth.user_presence;

CREATE TABLE auth.user_presence
(
    user_id    BIGINT PRIMARY KEY
        REFERENCES auth.users (id)
            ON DELETE CASCADE,

    is_online  BOOLEAN   NOT NULL,
    last_seen  TIMESTAMP NOT NULL,
    session_id VARCHAR(100),
    updated_at TIMESTAMP DEFAULT now()
);
