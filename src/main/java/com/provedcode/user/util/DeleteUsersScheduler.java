package com.provedcode.user.util;

import com.provedcode.user.repo.DeletedUserRepository;
import com.provedcode.user.repo.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@EnableScheduling
@AllArgsConstructor
public class DeleteUsersScheduler {
    UserInfoRepository userInfoRepository;
    DeletedUserRepository deletedUsersRepository;

    @Scheduled(fixedRate = 180000) // every 3 min
    public void runTask() {
        deletedUsersRepository.findAll()
                .forEach(user -> {
                    if (user.getTimeToDelete().isAfter(Instant.now())) {
                        deletedUsersRepository.delete(user);
                        userInfoRepository.deleteById(user.getDeletedUser().getId());
                    }
                });
    }
}
