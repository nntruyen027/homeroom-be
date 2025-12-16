DROP TYPE IF EXISTS auth.hoc_sinh_input CASCADE;

CREATE TYPE auth.hoc_sinh_input AS
(
    username  VARCHAR(120),
    password  VARCHAR(500),
    ho_ten    VARCHAR(500),
    lop_id    BIGINT,
    ngay_sinh VARCHAR(500),
    la_nam    VARCHAR(500)
);