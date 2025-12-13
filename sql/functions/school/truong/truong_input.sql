DROP TYPE IF EXISTS school.truong_input;

CREATE TYPE school.truong_input AS (
    ten varchar(120),
    dia_chi_chi_tiet varchar(500),
    xa_id bigint,
    hinh_anh text,
    logo text
);
