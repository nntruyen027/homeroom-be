DROP TABLE IF EXISTS school.lop_hoat_dong_huong_nghiep;

CREATE TABLE school.lop_hoat_dong_huong_nghiep
(
    hoat_dong_id BIGINT REFERENCES school.hoat_dong_huong_nghiep (id) ON DELETE CASCADE,
    lop_id       BIGINT REFERENCES school.lop (id) ON DELETE CASCADE,

    PRIMARY KEY (hoat_dong_id, lop_id)
);