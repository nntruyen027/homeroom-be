CREATE TABLE exam.de_thi_cau_hoi (
    id BIGSERIAL PRIMARY KEY,
    de_thi_id BIGINT,
    cau_hoi_id BIGINT,
    thu_tu INT,
    diem DOUBLE PRECISION,
    CONSTRAINT fk_de_thi FOREIGN KEY (de_thi_id) REFERENCES exam.de_thi(id) ON DELETE CASCADE,
    CONSTRAINT fk_cau_hoi FOREIGN KEY (cau_hoi_id) REFERENCES exam.cau_hoi(id) ON DELETE CASCADE
);
