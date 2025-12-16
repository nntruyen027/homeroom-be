package com.vinhthanh2.lophocdientu.repository;

import com.vinhthanh2.lophocdientu.dto.req.FileReq;
import com.vinhthanh2.lophocdientu.dto.res.FileRes;
import com.vinhthanh2.lophocdientu.dto.sql.FilePro;
import com.vinhthanh2.lophocdientu.mapper.FileMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FileRepo {
    @PersistenceContext
    private EntityManager entityManager;

    private final FileMapper fileMapper;

    @SuppressWarnings("unchecked")
    public List<FileRes> layTatCaFile(String search, int page, int limit) {
        int offset = (page - 1) * limit;
        String sql = """
                select * from fn_lay_tat_ca_file(
                              :p_search,
                              :p_offset,
                              :p_limit
                )
                """;

        List<FilePro> pros = entityManager.createNativeQuery(sql, FilePro.class)
                .setParameter("p_search", search)
                .setParameter("p_offset", offset)
                .setParameter("p_limit", limit)
                .getResultList()
                .stream().toList();

        return pros.stream().map(fileMapper::toDto).toList();
    }

    public Long demTatCaFile(String search) {
        String sql = """
                    SELECT fn_dem_tat_ca_file(:p_search)
                """;

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("p_search", search)
                .getSingleResult();

        if (result == null) return 0L;
        if (result instanceof Number num) return num.longValue();

        return Long.parseLong(result.toString());
    }

    public FileRes luuFile(FileReq fileReq) {
        String sql = """
                select * from fn_tao_file
                (
                    :p_file_name,
                    :p_stored_name,
                    :p_url,
                    :p_content_type,
                    :p_size,
                    :p_user_id
                )
                """;

        FilePro pro = (FilePro) entityManager.createNativeQuery(sql, FilePro.class)
                .setParameter("p_file_name", fileReq.getFileName())
                .setParameter("p_stored_name", fileReq.getStoredName())
                .setParameter("p_url", fileReq.getStoredName())
                .setParameter("p_size", fileReq.getSize())
                .setParameter("p_user_id", fileReq.getUserId())
                .getSingleResult();

        return fileMapper.toDto(pro);
    }

    public void xoaFile(Long id) {
        String sql = """
                select fn_xoa_file(:p_id);
                """;

        entityManager.createNativeQuery(sql)
                .setParameter("p_id", id)
                .getSingleResult();
    }
}
