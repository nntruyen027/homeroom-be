DROP TABLE IF EXISTS files;

CREATE TABLE files
(
    id           BIGSERIAL PRIMARY KEY,
    file_name    TEXT,
    stored_name  TEXT,
    url          TEXT,
    content_type VARCHAR(255),
    size         BIGINT,
    user_id      BIGINT,
    uploaded_at  TIMESTAMP,

    CONSTRAINT fk_user_files FOREIGN KEY (user_id) REFERENCES auth.users (id) ON DELETE SET NULL
);
