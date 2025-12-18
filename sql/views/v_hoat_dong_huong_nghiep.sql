DROP VIEW IF EXISTS school.v_hoat_dong_huong_nghiep;

CREATE OR REPLACE VIEW school.v_hoat_dong_huong_nghiep AS
SELECT hd.id      AS hoat_dong_id,
       hd.ten     AS ten_hoat_dong,
       hd.thoi_gian_bat_dau,
       hd.thoi_gian_ket_thuc,
       hd.ghi_chu,

       hd.nguoi_tao_id,
       u.username AS nguoi_tao_username,
       u.ho_ten   AS nguoi_tao_ho_ten,
       COALESCE(
                       array_agg(l.id ORDER BY l.id)
                       FILTER (WHERE l.id IS NOT NULL),
                       '{}'::bigint[]
       )          AS ds_lop_id
FROM school.hoat_dong_huong_nghiep hd
         LEFT JOIN auth.users u
                   ON u.id = hd.nguoi_tao_id
         LEFT OUTER JOIN school.lop_hoat_dong_huong_nghiep lhdhn on hd.id = lhdhn.hoat_dong_id
         LEFT JOIN school.lop l on l.id = lhdhn.lop_id
GROUP BY hd.id, hd.ten, hd.thoi_gian_bat_dau, hd.thoi_gian_ket_thuc, hd.ghi_chu, hd.nguoi_tao_id, u.username, u.ho_ten
;


