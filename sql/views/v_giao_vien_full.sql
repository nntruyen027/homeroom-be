DROP VIEW IF EXISTS auth.v_giao_vien_full CASCADE;
CREATE VIEW auth.v_giao_vien_full AS
SELECT u.id  AS user_id,
       u.username,
       u.ho_ten,
       u.avatar,

       gv.bo_mon,
       gv.chuc_vu,

       tt.ngay_sinh,
       tt.la_nam,
       tt.xa_id,
       x.ten as ten_xa,
       x.tinh_id,
       t.ten as ten_tinh,
       tt.dia_chi_chi_tiet
FROM auth.giao_vien gv
         JOIN auth.users u ON u.id = gv.user_id
         LEFT JOIN auth.thong_tin_nguoi_dung tt ON tt.user_id = u.id
         LEFT JOIN dm_chung.xa x on x.id = tt.xa_id
         left join dm_chung.tinh t on x.tinh_id = t.id
;
