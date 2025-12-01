package com.vinhthanh2.lophocdientu.controller.student;

import com.vinhthanh2.lophocdientu.dto.req.UpdateStudentReq;
import com.vinhthanh2.lophocdientu.service.AuthService;
import com.vinhthanh2.lophocdientu.service.HocSinhService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hoc-sinh")
@Getter
@Setter
@AllArgsConstructor
@PreAuthorize("hasRole('HOC_SINH')")
public class HocSinhController {
    private final HocSinhService hocSinhService;
    private final AuthService authService;

    @PutMapping("")
    public ResponseEntity<?> suaThongTinCaNhan(UpdateStudentReq updateStudentReq) {
        return ResponseEntity.ok(hocSinhService.suaThongTinCaNhan(updateStudentReq));
    }

}
