DROP FUNCTION IF EXISTS school.fn_tao_hoc_sinh;

CREATE OR REPLACE FUNCTION school.fn_tao_hoc_sinh(
    p_data JSONB
)
    RETURNS SETOF auth.v_users_hoc_sinh
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_new_id          BIGINT;
    v_student_role_id BIGINT;
BEGIN
    -- 1️⃣ Validate bắt buộc
    IF p_data ->> 'username' IS NULL THEN
        RAISE EXCEPTION 'username là bắt buộc';
    END IF;

    IF p_data ->> 'password' IS NULL THEN
        RAISE EXCEPTION 'password là bắt buộc';
    END IF;

    IF p_data ->> 'ho_ten' IS NULL THEN
        RAISE EXCEPTION 'ho_ten là bắt buộc';
    END IF;

    -- 2️⃣ Username unique
    IF EXISTS (SELECT 1
               FROM auth.users
               WHERE username = p_data ->> 'username') THEN
        RAISE EXCEPTION 'Username % đã tồn tại', p_data ->> 'username';
    END IF;

    -- 3️⃣ Validate lớp
    IF p_data ? 'lop_id' THEN
        IF NOT EXISTS (SELECT 1
                       FROM school.lop
                       WHERE id = (p_data ->> 'lop_id')::BIGINT) THEN
            RAISE EXCEPTION 'Lớp học với id % không tồn tại', p_data ->> 'lop_id';
        END IF;
    END IF;

    -- 4️⃣ Validate xã
    IF p_data ? 'xa_id' THEN
        IF NOT EXISTS (SELECT 1
                       FROM dm_chung.xa
                       WHERE id = (p_data ->> 'xa_id')::BIGINT) THEN
            RAISE EXCEPTION 'Xã với id % không tồn tại', p_data ->> 'xa_id';
        END IF;
    END IF;

    -- 5️⃣ Insert users
    INSERT INTO auth.users (username,
                            password,
                            avatar,
                            ho_ten,
                            lop_id,
                            ngay_sinh,
                            la_nam,
                            so_thich,
                            mon_hoc_yeu_thich,
                            diem_manh,
                            diem_yeu,
                            nghe_nghiep_mong_muon,
                            nhan_xet_giao_vien,
                            ghi_chu,
                            realistic_score,
                            investigative_score,
                            artistic_score,
                            social_score,
                            enterprising_score,
                            conventional_score,
                            assessment_result,
                            ten_cha,
                            ns_cha,
                            sdt_cha,
                            ten_me,
                            ns_me,
                            sdt_me,
                            ten_ph_khac,
                            ns_ph_khac,
                            sdt_ph_khac,
                            xa_id)
    VALUES (p_data ->> 'username',
            p_data ->> 'password',
            p_data ->> 'avatar',
            p_data ->> 'ho_ten',
            (p_data ->> 'lop_id')::BIGINT,
            p_data ->> 'ngay_sinh',
            (p_data ->> 'la_nam')::BOOLEAN,
            p_data ->> 'so_thich',
            p_data ->> 'mon_hoc_yeu_thich',
            p_data ->> 'diem_manh',
            p_data ->> 'diem_yeu',
            p_data ->> 'nghe_nghiep_mong_muon',
            p_data ->> 'nhan_xet_giao_vien',
            p_data ->> 'ghi_chu',
            (p_data ->> 'realistic_score')::INT,
            (p_data ->> 'investigative_score')::INT,
            (p_data ->> 'artistic_score')::INT,
            (p_data ->> 'social_score')::INT,
            (p_data ->> 'enterprising_score')::INT,
            (p_data ->> 'conventional_score')::INT,
            p_data ->> 'assessment_result',
            p_data ->> 'ten_cha',
            p_data ->> 'ns_cha',
            p_data ->> 'sdt_cha',
            p_data ->> 'ten_me',
            p_data ->> 'ns_me',
            p_data ->> 'sdt_me',
            p_data ->> 'ten_ph_khac',
            p_data ->> 'ns_ph_khac',
            p_data ->> 'sdt_ph_khac',
            (p_data ->> 'xa_id')::BIGINT)
    RETURNING id INTO v_new_id;

    -- 6️⃣ Gán role STUDENT
    SELECT id
    INTO v_student_role_id
    FROM auth.roles
    WHERE code = 'STUDENT';

    INSERT INTO auth.user_roles(user_id, role_id)
    VALUES (v_new_id, v_student_role_id);

    -- 7️⃣ Trả về VIEW chuẩn
    RETURN QUERY
        SELECT *
        FROM auth.v_users_hoc_sinh
        WHERE out_id = v_new_id;
END;
$$;
