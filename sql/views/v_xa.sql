DROP VIEW IF EXISTS dm_chung.v_xa;


CREATE OR REPLACE VIEW dm_chung.v_xa AS
SELECT x.id  AS out_id,
       x.ten,
       x.ghi_chu,
       x.tinh_id,
       t.ten AS ten_tinh
FROM dm_chung.xa x
         LEFT JOIN dm_chung.tinh t ON t.id = x.tinh_id