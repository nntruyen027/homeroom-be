DROP TABLE IF EXISTS dm_chung.nghe_nghiep;

CREATE TABLE dm_chung.nghe_nghiep
(
    id         BIGSERIAL PRIMARY KEY,
    ten        VARCHAR(255) NOT NULL,

    mo_ta      TEXT,
    thu_tu     INT       DEFAULT 0,

    is_active  BOOLEAN   DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT now()
);
