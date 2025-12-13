DROP VIEW IF EXISTS dm_chung.v_tinh;

CREATE OR REPLACE VIEW dm_chung.v_tinh AS
SELECT t.id as out_id,
       t.ten,
       t.ghi_chu
FROM dm_chung.tinh t;