CREATE TABLE auth.users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(120) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    avatar TEXT,
    ho_ten TEXT NOT NULL,
    xa_id BIGINT,
    dia_chi_chi_tiet VARCHAR(500)

    -- Khóa ngoại đến bảng lop (học sinh thuộc lớp nào)
    lop_id BIGINT,

    ngay_sinh VARCHAR(50),
    la_nam BOOLEAN,
    so_thich TEXT,
    mon_hoc_yeu_thich TEXT,
    diem_manh TEXT,
    diem_yeu TEXT,
    nghe_nghiep_mong_muon TEXT,
    nhan_xet_giao_vien TEXT,
    ghi_chu TEXT,

    realistic_score INT,
    investigative_score INT,
    artistic_score INT,
    social_score INT,
    enterprising_score INT,
    conventional_score INT,

    assessment_result VARCHAR(500),

    -- Thông tin phụ huynh
    ten_cha TEXT,
    ns_cha VARCHAR(50),
    sdt_cha VARCHAR(20),
    ten_me TEXT,
    ns_me VARCHAR(50),
    sdt_me VARCHAR(20),
    ten_ph_khac TEXT,
    ns_ph_khac VARCHAR(50),
    sdt_ph_khac VARCHAR(20),

    -- Dành cho giáo viên
    bo_mon TEXT,
    chuc_vu TEXT,

    CONSTRAINT fk_xa FOREIGN KEY (xa_id) REFERENCES dm_chung.xa(id) ON DELETE SET NULL
);

