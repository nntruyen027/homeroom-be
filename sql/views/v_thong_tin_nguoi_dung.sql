DROP VIEW IF EXISTS auth.v_thong_tin_nguoi_dung CASCADE;
CREATE VIEW auth.v_thong_tin_nguoi_dung AS
SELECT tt.user_id,

       tt.ngay_sinh,
       tt.la_nam,
       tt.dia_chi_chi_tiet,

       -- Xã
       xa.id  AS xa_id,
       xa.ten AS ten_xa,

       -- Tỉnh
       t.id   AS tinh_id,
       t.ten  AS ten_tinh
FROM auth.thong_tin_nguoi_dung tt
         LEFT JOIN dm_chung.xa xa
                   ON xa.id = tt.xa_id
         LEFT JOIN dm_chung.tinh t
                   ON t.id = xa.tinh_id;
