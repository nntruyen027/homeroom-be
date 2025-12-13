CREATE TABLE exam.bai_lam (
    id BIGSERIAL PRIMARY KEY,
    lan_thi_id BIGINT NOT NULL,
    cau_hoi_id BIGINT NOT NULL,
    dap_an_hoc_sinh TEXT,
    dung BOOLEAN,
    diem DOUBLE PRECISION,
    CONSTRAINT fk_lan_thi FOREIGN KEY (lan_thi_id) REFERENCES exam.lan_thi(id) ON DELETE CASCADE,
    CONSTRAINT fk_cau_hoi FOREIGN KEY (cau_hoi_id) REFERENCES exam.cau_hoi(id) ON DELETE CASCADE
);
