package com.vinhthanh2.lophocdientu.websocket.scheduler;

import com.vinhthanh2.lophocdientu.repository.UserPresenceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class PresenceScheduler {

    private final UserPresenceRepo repo;

    @Scheduled(fixedRate = 60000) // mỗi phút
    public void autoOffline() {
        repo.autoOffline(5);
    }
}
