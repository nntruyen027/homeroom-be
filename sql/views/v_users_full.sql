DROP VIEW IF EXISTS auth.v_users_full;

CREATE OR REPLACE VIEW auth.v_users_full AS
SELECT u.id                                           AS out_id,
       u.username,
       u.password,
       u.avatar,
       u.ho_ten,

       u.xa_id,
       x.ten                                          AS ten_xa,
       x.tinh_id                                      AS tinh_id,
       ti.ten                                         AS ten_tinh,

       u.dia_chi_chi_tiet,

       u.lop_id,
       l.ten                                          AS ten_lop,
       l.truong_id                                    AS truong_id,
       t.ten                                          AS ten_truong,

       -- 👇 ROLE (đa vai trò)
       COALESCE(string_agg(DISTINCT r.code, ','), '') AS roles,

       u.ngay_sinh,
       u.la_nam,
       u.so_thich,
       u.mon_hoc_yeu_thich,
       u.diem_manh,
       u.diem_yeu,
       u.nghe_nghiep_mong_muon,
       u.nhan_xet_giao_vien,
       u.ghi_chu,

       u.realistic_score,
       u.investigative_score,
       u.artistic_score,
       u.social_score,
       u.enterprising_score,
       u.conventional_score,

       u.assessment_result,

       -- Phụ huynh
       u.ten_cha,
       u.ns_cha,
       u.sdt_cha,
       u.ten_me,
       u.ns_me,
       u.sdt_me,
       u.ten_ph_khac,
       u.ns_ph_khac,
       u.sdt_ph_khac,

       -- Giáo viên
       u.bo_mon,
       u.chuc_vu,
       -- 👇 PERMISSION (từ tất cả role của user)
       COALESCE(string_agg(DISTINCT p.code, ','), '') AS permissions


FROM auth.users u
         LEFT JOIN auth.user_roles ur ON ur.user_id = u.id
         LEFT JOIN auth.roles r ON r.id = ur.role_id
         LEFT JOIN auth.role_permissions rp ON rp.role_id = r.id
         LEFT JOIN auth.permissions p ON p.id = rp.permission_id
         LEFT JOIN dm_chung.xa x ON x.id = u.xa_id
         LEFT JOIN dm_chung.tinh ti ON ti.id = x.tinh_id
         LEFT JOIN school.lop l ON l.id = u.lop_id
         LEFT JOIN school.truong t ON t.id = l.truong_id

GROUP BY u.id,
         u.username,
         u.password,
         u.avatar,
         u.ho_ten,
         u.xa_id,
         x.ten,
         x.tinh_id,
         ti.ten,
         u.dia_chi_chi_tiet,
         u.lop_id,
         l.ten,
         l.truong_id,
         t.ten,
         u.ngay_sinh,
         u.la_nam,
         u.so_thich,
         u.mon_hoc_yeu_thich,
         u.diem_manh,
         u.diem_yeu,
         u.nghe_nghiep_mong_muon,
         u.nhan_xet_giao_vien,
         u.ghi_chu,
         u.realistic_score,
         u.investigative_score,
         u.artistic_score,
         u.social_score,
         u.enterprising_score,
         u.conventional_score,
         u.assessment_result,
         u.ten_cha,
         u.ns_cha,
         u.sdt_cha,
         u.ten_me,
         u.ns_me,
         u.sdt_me,
         u.ten_ph_khac,
         u.ns_ph_khac,
         u.sdt_ph_khac,
         u.bo_mon,
         u.chuc_vu;
