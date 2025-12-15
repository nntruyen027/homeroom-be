DROP VIEW IF EXISTS auth.v_phu_huynh_full CASCADE;
CREATE VIEW auth.v_phu_huynh_full AS
SELECT u.id   AS user_id,
       u.username,
       u.ho_ten,
       u.avatar,

       ph.hoc_sinh_id,
       ph.loai_phu_huynh,
       ph.so_dien_thoai,

       nn.id  AS nghe_nghiep_id,
       nn.ten AS nghe_nghiep,

       tt.ngay_sinh,
       tt.la_nam,
       tt.xa_id,
       x.ten  as ten_xa,
       x.tinh_id,
       ti.ten as ten_tinh,
       tt.dia_chi_chi_tiet
FROM auth.phu_huynh ph
         JOIN auth.users u ON u.id = ph.user_id
         LEFT JOIN auth.thong_tin_nguoi_dung tt ON tt.user_id = u.id
         left join dm_chung.xa x on x.id = tt.xa_id
         left join dm_chung.tinh ti on ti.id = x.tinh_id
         LEFT JOIN dm_chung.nghe_nghiep nn
                   ON nn.id = ph.nghe_nghiep_id;
