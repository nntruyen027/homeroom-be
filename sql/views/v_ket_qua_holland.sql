CREATE OR REPLACE VIEW school.v_ket_qua_holland AS
SELECT kq.id,
       kq.hs_id,
       tt.ho_ten,
       hs.lop_id,

       kq.diem_r,
       kq.diem_i,
       kq.diem_a,
       kq.diem_s,
       kq.diem_e,
       kq.diem_c,

       kq.ma_holland,
       kq.thoi_gian_lam,
       kq.ngay_danh_gia
FROM school.ket_qua_holland kq
         LEFT JOIN auth.hoc_sinh hs ON kq.hs_id = hs.user_id
         LEFT JOIN auth.users tt ON tt.id = kq.hs_id;
