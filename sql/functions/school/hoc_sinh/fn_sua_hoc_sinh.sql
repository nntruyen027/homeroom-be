DROP FUNCTION IF EXISTS school.fn_sua_hoc_sinh;

CREATE OR REPLACE FUNCTION school.fn_sua_hoc_sinh(
    p_id BIGINT,
    p_data JSONB
)
    RETURNS SETOF auth.v_users_hoc_sinh
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- 1️⃣ Kiểm tra học sinh tồn tại & đúng role STUDENT
    IF NOT EXISTS (SELECT 1
                   FROM auth.user_roles ur
                            JOIN auth.roles r ON r.id = ur.role_id AND r.code = 'STUDENT'
                   WHERE ur.user_id = p_id) THEN
        RAISE EXCEPTION 'Học sinh với id % không tồn tại', p_id;
    END IF;

    -- 2️⃣ Validate username (nếu có)
    IF p_data ? 'username' THEN
        IF EXISTS (SELECT 1
                   FROM auth.users
                   WHERE username = p_data ->> 'username'
                     AND id <> p_id) THEN
            RAISE EXCEPTION 'Username % đã tồn tại', p_data ->> 'username';
        END IF;
    END IF;

    -- 3️⃣ Validate lớp (nếu có)
    IF p_data ? 'lop_id' THEN
        IF NOT EXISTS (SELECT 1
                       FROM school.lop
                       WHERE id = (p_data ->> 'lop_id')::BIGINT) THEN
            RAISE EXCEPTION 'Lớp học với id % không tồn tại', p_data ->> 'lop_id';
        END IF;
    END IF;

    -- 4️⃣ Validate xã (nếu có)
    IF p_data ? 'xa_id' THEN
        IF NOT EXISTS (SELECT 1
                       FROM dm_chung.xa
                       WHERE id = (p_data ->> 'xa_id')::BIGINT) THEN
            RAISE EXCEPTION 'Xã với id % không tồn tại', p_data ->> 'xa_id';
        END IF;
    END IF;

    -- 5️⃣ UPDATE linh hoạt (field nào có thì update)
    UPDATE auth.users u
    SET username              = COALESCE(p_data ->> 'username', u.username),
        password              = COALESCE(p_data ->> 'password', u.password),
        avatar                = COALESCE(p_data ->> 'avatar', u.avatar),
        ho_ten                = COALESCE(p_data ->> 'ho_ten', u.ho_ten),
        lop_id                = COALESCE((p_data ->> 'lop_id')::BIGINT, u.lop_id),
        ngay_sinh             = COALESCE(p_data ->> 'ngay_sinh', u.ngay_sinh),
        la_nam                = COALESCE((p_data ->> 'la_nam')::BOOLEAN, u.la_nam),
        so_thich              = COALESCE(p_data ->> 'so_thich', u.so_thich),
        mon_hoc_yeu_thich     = COALESCE(p_data ->> 'mon_hoc_yeu_thich', u.mon_hoc_yeu_thich),
        diem_manh             = COALESCE(p_data ->> 'diem_manh', u.diem_manh),
        diem_yeu              = COALESCE(p_data ->> 'diem_yeu', u.diem_yeu),
        nghe_nghiep_mong_muon = COALESCE(p_data ->> 'nghe_nghiep_mong_muon', u.nghe_nghiep_mong_muon),
        nhan_xet_giao_vien    = COALESCE(p_data ->> 'nhan_xet_giao_vien', u.nhan_xet_giao_vien),
        ghi_chu               = COALESCE(p_data ->> 'ghi_chu', u.ghi_chu),

        realistic_score       = COALESCE((p_data ->> 'realistic_score')::INT, u.realistic_score),
        investigative_score   = COALESCE((p_data ->> 'investigative_score')::INT, u.investigative_score),
        artistic_score        = COALESCE((p_data ->> 'artistic_score')::INT, u.artistic_score),
        social_score          = COALESCE((p_data ->> 'social_score')::INT, u.social_score),
        enterprising_score    = COALESCE((p_data ->> 'enterprising_score')::INT, u.enterprising_score),
        conventional_score    = COALESCE((p_data ->> 'conventional_score')::INT, u.conventional_score),
        assessment_result     = COALESCE(p_data ->> 'assessment_result', u.assessment_result),

        ten_cha               = COALESCE(p_data ->> 'ten_cha', u.ten_cha),
        ns_cha                = COALESCE(p_data ->> 'ns_cha', u.ns_cha),
        sdt_cha               = COALESCE(p_data ->> 'sdt_cha', u.sdt_cha),
        ten_me                = COALESCE(p_data ->> 'ten_me', u.ten_me),
        ns_me                 = COALESCE(p_data ->> 'ns_me', u.ns_me),
        sdt_me                = COALESCE(p_data ->> 'sdt_me', u.sdt_me),
        ten_ph_khac           = COALESCE(p_data ->> 'ten_ph_khac', u.ten_ph_khac),
        ns_ph_khac            = COALESCE(p_data ->> 'ns_ph_khac', u.ns_ph_khac),
        sdt_ph_khac           = COALESCE(p_data ->> 'sdt_ph_khac', u.sdt_ph_khac),

        xa_id                 = COALESCE((p_data ->> 'xa_id')::BIGINT, u.xa_id)

    WHERE u.id = p_id;

    -- 6️⃣ Trả về VIEW đã chuẩn hoá
    RETURN QUERY
        SELECT *
        FROM auth.v_users_hoc_sinh hs
        WHERE hs.out_id = p_id;
END;
$$;
