CREATE OR REPLACE VIEW school.vw_thong_ke_holland_theo_lop_top2 AS
WITH latest_kq AS (
    -- Lấy kết quả Holland mới nhất của mỗi học sinh
    SELECT DISTINCT ON (hs_id) *
    FROM school.ket_qua_holland
    ORDER BY hs_id, ngay_danh_gia DESC)
SELECT hs.lop_id,
       COUNT(*)                                                                 AS so_luong_hoc_sinh,
       COUNT(*) FILTER (WHERE substring(kq.ma_holland from 1 for 2) LIKE '%R%') AS top2_r,
       COUNT(*) FILTER (WHERE substring(kq.ma_holland from 1 for 2) LIKE '%I%') AS top2_i,
       COUNT(*) FILTER (WHERE substring(kq.ma_holland from 1 for 2) LIKE '%A%') AS top2_a,
       COUNT(*) FILTER (WHERE substring(kq.ma_holland from 1 for 2) LIKE '%S%') AS top2_s,
       COUNT(*) FILTER (WHERE substring(kq.ma_holland from 1 for 2) LIKE '%E%') AS top2_e,
       COUNT(*) FILTER (WHERE substring(kq.ma_holland from 1 for 2) LIKE '%C%') AS top2_c

FROM latest_kq kq
         JOIN auth.hoc_sinh hs ON hs.user_id = kq.hs_id
GROUP BY hs.lop_id
ORDER BY hs.lop_id;