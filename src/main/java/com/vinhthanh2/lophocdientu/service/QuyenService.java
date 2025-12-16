package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.res.PageResponse;
import com.vinhthanh2.lophocdientu.dto.res.PermissionRes;
import com.vinhthanh2.lophocdientu.mapper.PermissionMapper;
import com.vinhthanh2.lophocdientu.repository.PermissionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuyenService {
    private final PermissionRepo permissionRepo;
    private final PermissionMapper permissionMapper;

    public PageResponse<PermissionRes> layTatCaQuyen(String search, int page, int size) {

        List<PermissionRes> data = permissionRepo.layTatCaQuyen(search, page, size)
                .stream().toList();

        long totalElements = permissionRepo.demTatCaQuyen(search);

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<PermissionRes>builder()
                .data(data)
                .size(size)
                .page(page)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }
}
