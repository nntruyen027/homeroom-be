DROP VIEW IF EXISTS school.v_truong;

CREATE OR REPLACE VIEW school.v_truong AS
SELECT t.id,
       t.ten,
       t.xa_id,
       x.ten  AS ten_xa,
       ti.id  AS tinh_id,
       ti.ten AS ten_tinh,
       t.dia_chi_chi_tiet,
       t.hinh_anh,
       t.logo
FROM school.truong t
         LEFT JOIN dm_chung.xa x ON x.id = t.xa_id
         LEFT JOIN dm_chung.tinh ti ON ti.id = x.tinh_id