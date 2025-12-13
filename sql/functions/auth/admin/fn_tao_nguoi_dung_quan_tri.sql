DROP FUNCTION IF EXISTS auth.fn_tao_nguoi_dung_quan_tri;

CREATE OR REPLACE FUNCTION auth.fn_tao_nguoi_dung_quan_tri(
    p_username VARCHAR(120),
    p_ho_ten TEXT,
    p_password TEXT,
    p_avatar TEXT
)
    RETURNS SETOF auth.v_users_full
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_user_id         BIGINT;
    v_role_id         BIGINT;
    v_permission_id   BIGINT;
    v_permission_code TEXT;
BEGIN
    /* ===============================
       1️⃣ KIỂM TRA USERNAME
       =============================== */
    IF EXISTS (SELECT 1
               FROM auth.users
               WHERE username = p_username) THEN
        RAISE EXCEPTION 'Username % đã tồn tại', p_username;
    END IF;

    /* ===============================
       2️⃣ KHỞI TẠO ROLE ADMIN NẾU CHƯA CÓ
       =============================== */
    SELECT id
    INTO v_role_id
    FROM auth.roles
    WHERE code = 'ADMIN';

    IF v_role_id IS NULL THEN
        INSERT INTO auth.roles(code, name)
        VALUES ('ADMIN', 'Quản trị hệ thống')
        RETURNING id INTO v_role_id;
    END IF;

    /* ===============================
       3️⃣ KHỞI TẠO PERMISSION + GÁN CHO ADMIN
       =============================== */
    FOREACH v_permission_code IN ARRAY ARRAY [
        'USER_MANAGE',
        'ROLE_MANAGE',
        'CLASS_MANAGE',
        'SCHOOL_MANAGE',
        'STUDENT_MANAGE',
        'TEACHER_MANAGE',
        'SYSTEM_CONFIG',
        'CATEGORY_MANAGE'
        ]
        LOOP
            -- tạo permission nếu chưa có
            SELECT id
            INTO v_permission_id
            FROM auth.permissions
            WHERE code = v_permission_code;

            IF v_permission_id IS NULL THEN
                INSERT INTO auth.permissions(code)
                VALUES (v_permission_code)
                RETURNING id INTO v_permission_id;
            END IF;

            -- gán permission cho role admin (nếu chưa gán)
            IF NOT EXISTS (SELECT 1
                           FROM auth.role_permissions
                           WHERE role_id = v_role_id
                             AND permission_id = v_permission_id) THEN
                INSERT INTO auth.role_permissions(role_id, permission_id)
                VALUES (v_role_id, v_permission_id);
            END IF;
        END LOOP;

    /* ===============================
       4️⃣ TẠO USER
       =============================== */
    INSERT INTO auth.users(username,
                           password,
                           avatar,
                           ho_ten)
    VALUES (p_username,
            p_password,
            p_avatar,
            p_ho_ten)
    RETURNING id INTO v_user_id;

    /* ===============================
       5️⃣ GÁN ROLE ADMIN CHO USER
       =============================== */
    INSERT INTO auth.user_roles(user_id, role_id)
    VALUES (v_user_id, v_role_id);

    /* ===============================
       6️⃣ TRẢ VỀ RECORD USER TỪ VIEW
       =============================== */
    RETURN QUERY
        SELECT *
        FROM auth.v_users_full
        WHERE out_id = v_user_id;

END;
$$;
