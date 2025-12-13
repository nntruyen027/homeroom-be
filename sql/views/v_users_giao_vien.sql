DROP VIEW IF EXISTS auth.v_users_giao_vien;

CREATE OR REPLACE VIEW auth.v_users_giao_vien AS
SELECT u.id               AS out_id,
       u.username         AS username,
       u.ho_ten           AS ho_ten,
       u.avatar           AS avatar,
       u.ngay_sinh        AS ngay_sinh,
       u.la_nam           AS la_nam,
       u.bo_mon           AS bo_mon,
       u.chuc_vu          AS chuc_vu,

       u.dia_chi_chi_tiet AS dia_chi_chi_tiet,

       x.id               AS xa_id,
       x.ten              AS ten_xa,
       ti.id              AS tinh_id,
       ti.ten             AS ten_tinh,

       r.code             AS role_name
FROM auth.users u
         JOIN auth.user_roles ur ON ur.user_id = u.id
         JOIN auth.roles r ON r.id = ur.role_id AND r.code = 'TEACHER'
         LEFT JOIN dm_chung.xa x ON x.id = u.xa_id
         LEFT JOIN dm_chung.tinh ti ON ti.id = x.tinh_id
         LEFT JOIN
         JOIN school.lop l ON l.giao_vien_id = u.id
