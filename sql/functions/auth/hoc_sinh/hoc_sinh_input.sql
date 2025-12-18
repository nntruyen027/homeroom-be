DROP TYPE IF EXISTS auth.hoc_sinh_input;

CREATE TYPE auth.hoc_sinh_input AS
(
    username  VARCHAR(120),
    password  VARCHAR(500),
    ho_ten    VARCHAR(500),
    lop_id    BIGINT,
    ngay_sinh DATE,
    la_nam    BIGINT
);

ALTER TYPE auth.hoc_sinh_input
    ALTER ATTRIBUTE la_nam TYPE VARCHAR(500);