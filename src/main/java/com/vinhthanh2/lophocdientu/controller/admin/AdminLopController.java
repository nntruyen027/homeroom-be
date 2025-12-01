package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.dto.req.LopReq;
import com.vinhthanh2.lophocdientu.dto.res.LopRes;
import com.vinhthanh2.lophocdientu.dto.res.TeacherRes;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.LopService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quan-tri/lop")
@PreAuthorize("hasRole('TEACHER')")
@AllArgsConstructor
public class AdminLopController {
    final private LopService lopService;
    final private AuthService authService;

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
