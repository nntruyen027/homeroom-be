DROP VIEW IF EXISTS school.v_lop;

CREATE OR REPLACE VIEW school.v_lop AS
SELECT l.id     AS out_id,
       l.ten,
       l.hinh_anh,
       l.truong_id,
       t.ten    AS ten_truong,
       l.giao_vien_id,
       u.ho_ten AS ten_giao_vien
FROM school.lop l
         LEFT JOIN auth.users u ON u.id = l.giao_vien_id
         LEFT JOIN school.truong t ON t.id = l.truong_id