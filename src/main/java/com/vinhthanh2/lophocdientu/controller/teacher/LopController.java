package com.vinhthanh2.lophocdientu.controller.teacher;

import com.vinhthanh2.lophocdientu.dto.req.LopReq;
import com.vinhthanh2.lophocdientu.dto.res.LopRes;
import com.vinhthanh2.lophocdientu.dto.res.TeacherRes;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.LopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/giao-vien/lop")
@PreAuthorize("hasRole('TEACHER')")
@AllArgsConstructor
public class LopController {
    final private LopService lopService;
    final private AuthService authService;

    @GetMapping
    public ResponseEntity<?> layLopTuTao(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(lopService.layDsLopTheoGv(
                ((TeacherRes) authService.getCurrentUserDto()).getId(),
                search,
                page,
                size
        ));
    }

    @PostMapping
    public LopRes taoLop(@RequestBody LopReq lopReq) {
        lopReq.setGiaoVienId(((TeacherRes) authService.getCurrentUserDto()).getId());
        return lopService.taoLop(lopReq);
    }

    @PutMapping("/{id}")
    public LopRes suaLop(@PathVariable Long id, @RequestBody LopReq lopReq) {
        lopReq.setGiaoVienId(((TeacherRes) authService.getCurrentUserDto()).getId());
        return lopService.suaLop(id, lopReq);
    }

    @DeleteMapping("/{id}")
    public void xoaLop(@PathVariable Long id) {
        lopService.xoaLop(id);
    }
}
