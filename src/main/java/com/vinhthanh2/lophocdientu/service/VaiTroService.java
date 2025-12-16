package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.req.RoleReq;
import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.RoleRes;
import com.vinhthanh2.lophocdientu.mapper.RoleMapper;
import com.vinhthanh2.lophocdientu.repository.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VaiTroService {
    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;

    public PageResponse<RoleRes> layTatCaVaiTro(String search, int page, int size) {
        List<RoleRes> data = roleRepo.layTatCaVaiTro(search, page, size)
                .stream().toList();

        long totalElements = roleRepo.demTatCaVaiTro(search);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<RoleRes>builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .page(page)
                .size(size)
                .data(data)
                .build();
    }

    public RoleRes phanQuyenChoVaiTro(Long id, List<String> permissions) {
        return (roleRepo.phanQuyenChoVaiTro(id, permissions));
    }

    public RoleRes taoVaiTro(RoleReq roleReq) {
        return (roleRepo.taoVaiTro(roleReq));
    }

    public RoleRes suaVaiTro(Long id, RoleReq roleReq) {
        return (roleRepo.suaVaiTro(id, roleReq));
    }

    public void xoaVaiTro(Long id) {
        roleRepo.xoaVaiTro(id);
    }
}
