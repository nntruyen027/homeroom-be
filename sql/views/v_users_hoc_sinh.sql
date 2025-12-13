DROP VIEW IF EXISTS auth.v_users_hoc_sinh;

CREATE OR REPLACE VIEW auth.v_users_hoc_sinh AS
SELECT u.id   AS out_id,
       u.username,
       u.avatar,
       r.code AS role_name,

       u.ho_ten,
       u.xa_id,
       x.ten  AS ten_xa,
       ti.id  AS tinh_id,
       ti.ten AS ten_tinh,
       u.dia_chi_chi_tiet,

       u.ngay_sinh,
       u.la_nam,
       u.so_thich,

       u.lop_id,
       l.ten  AS ten_lop,
       l.truong_id,
       t.ten  AS ten_truong,

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
       u.sdt_ph_khac

FROM auth.users u
         JOIN auth.user_roles ur ON ur.user_id = u.id
         JOIN auth.roles r ON r.id = ur.role_id AND r.code = 'STUDENT'
         LEFT JOIN dm_chung.xa x ON x.id = u.xa_id
         LEFT JOIN dm_chung.tinh ti ON ti.id = x.tinh_id
         LEFT JOIN school.lop l ON l.id = u.lop_id
         LEFT JOIN school.truong t ON t.id = l.truong_id;
