CREATE TABLE exam.an_thi (
    id BIGSERIAL PRIMARY KEY,
    
    -- Khóa ngoại học sinh
    hoc_sinh_id BIGINT NOT NULL,
    
    -- Khóa ngoại đề thi
    de_thi_id BIGINT NOT NULL,
    
    bat_dau TIMESTAMP,
    ket_thuc TIMESTAMP,
    tong_diem DOUBLE PRECISION,
    nhan_xet TEXT,
    
    -- Ràng buộc khóa ngoại
    CONSTRAINT fk_hoc_sinh FOREIGN KEY (hoc_sinh_id) REFERENCES auth.users(id) ON DELETE CASCADE,
    CONSTRAINT fk_de_thi FOREIGN KEY (de_thi_id) REFERENCES exam.de_thi(id) ON DELETE CASCADE
);
