DROP VIEW IF EXISTS auth.v_permission;

CREATE OR REPLACE VIEW auth.v_permission AS
SELECT id as out_id,
       code
FROM auth.permissions