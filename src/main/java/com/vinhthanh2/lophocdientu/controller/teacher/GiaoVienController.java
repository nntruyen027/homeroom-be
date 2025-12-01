package com.vinhthanh2.lophocdientu.controller.teacher;

import com.vinhthanh2.lophocdientu.dto.req.UpdateTeacherReq;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.GiaoVienService;
import com.vinhthanh2.lophocdientu.service.LopService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/giao-vien")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class GiaoVienController {
    private final GiaoVienService giaoVienService;
    private final AuthService authService;
    private final LopService lopService;

    @GetMapping("/{id}")
    public ResponseEntity<?> layGiaoVien(Long id) {
        return ResponseEntity.ok(giaoVienService.layGiaoVienTheoId(id));
    }


    @PutMapping("")
    public ResponseEntity<?> suaThongTinCaNhan(@RequestBody UpdateTeacherReq updateTeacherReq) {
        return ResponseEntity.ok(giaoVienService.suaThongTinCaNhan(updateTeacherReq));
    }
}
