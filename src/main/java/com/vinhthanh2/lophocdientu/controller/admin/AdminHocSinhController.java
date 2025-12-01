package com.vinhthanh2.lophocdientu.controller.admin;

import com.vinhthanh2.lophocdientu.dto.req.UpdatePassReq;
import com.vinhthanh2.lophocdientu.dto.req.UpdateStudentReq;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.HocSinhService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quan-tri/hocsinh")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminHocSinhController {
    private final HocSinhService hocSinhService;
    private final AuthService authService;

    @GetMapping("/lop/{lopId}")
    public ResponseEntity<?> layDsHocSinhTheoLop(@PathVariable Long lopId,
                                                 @RequestParam(required = false, defaultValue = "") String search,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(hocSinhService.layHocSinhTheoLop(lopId, search, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> layHocSinhTheoId(@PathVariable Long id) {
        return ResponseEntity.ok(hocSinhService.layHocSinhTheoId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> suaHocSinh(Long id, UpdateStudentReq updateStudentRe) {
        return ResponseEntity.ok(hocSinhService.suaHocSinh(id, updateStudentRe));
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<?> datLaiMatKhau(Long id, UpdatePassReq updatePassReq) {
        authService.doiMatKhau(id, updatePassReq);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaHocSinh(@PathVariable Long id) {
        hocSinhService.xoaHocSinh(id);
        return ResponseEntity.ok().build();
    }
}
