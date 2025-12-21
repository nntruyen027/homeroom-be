package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.res.HocSinhRes;
import com.vinhthanh2.lophocdientu.dto.res.HsOnlRes;
import com.vinhthanh2.lophocdientu.dto.sql.UserAuthPro;
import com.vinhthanh2.lophocdientu.repository.UserPresenceRepo;
import com.vinhthanh2.lophocdientu.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PresenceService {

    private final UserPresenceRepo repo;
    private final UserRepo userRepository;
    private final HocSinhService hocSinhService;
    private final SimpMessagingTemplate messagingTemplate;

    // =========================
    // WS CONNECT
    // =========================
    public void onConnect(String username, String sessionId) {
        UserAuthPro user = userRepository.findAuthByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng !"));

        repo.userOnline(user.getId(), sessionId);

        // Chỉ học sinh mới ảnh hưởng realtime theo lớp
        if (Arrays.asList(user.getRoles()).contains("STUDENT")) {
            HocSinhRes hs = hocSinhService.layHsTheoId(user.getId());
            dsHocSinhOnline(hs.getLop().getId());
        }
    }

    // =========================
    // WS DISCONNECT
    // =========================
    public void onDisconnect(String sessionId) {
        Optional<Long> lopIdOpt = repo.findLopIdBySession(sessionId);

        repo.userOfflineBySession(sessionId);

        lopIdOpt.ifPresent(this::dsHocSinhOnline);
    }

    // =========================
    // REST / INTERNAL
    // =========================
    public Long countOnlineByLop(Long lopId) {
        return repo.countHocSinhOnline(lopId);
    }

    public void dsHocSinhOnline(Long lopId) {
        // 1️⃣ Số HS online
        Long count = this.countOnlineByLop(lopId);
        messagingTemplate.convertAndSend(
                "/topic/lop/" + lopId + "/online-count",
                count
        );

        // 2️⃣ Danh sách HS online
        List<HsOnlRes> ds = repo.danhSachHocSinhOnline(lopId);
        messagingTemplate.convertAndSend(
                "/topic/lop/" + lopId + "/online-list",
                ds
        );
    }
}
