CREATE TABLE exam.cau_hoi (
    id BIGSERIAL PRIMARY KEY,
    cau_hoi TEXT NOT NULL,
    nhom_id BIGINT,
    cau_a TEXT,
    cau_b TEXT,
    cau_c TEXT,
    cau_d TEXT,
    tu_luan TEXT,
    dap_an TEXT,
    diem_mac_dinh TEXT,
    CONSTRAINT fk_nhom FOREIGN KEY (nhom_id) REFERENCES exam.nhom_cau_hoi(id) ON DELETE CASCADE
);
