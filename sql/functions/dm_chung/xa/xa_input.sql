DROP TYPE IF EXISTS dm_chung.xa_input;

CREATE TYPE dm_chung.xa_input AS (
    ten varchar(120),
    ghi_chu TEXT,
    tinh_id BIGINT
);
