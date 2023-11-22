package com.recordsystem.auth.init;

import com.recordsystem.auth.dto.CreateUserRequest;
import com.recordsystem.auth.models.UserRole;
import com.recordsystem.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitialAdminUserSetup implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${user-service.admin.email}")
    private String adminEmail;

    @Value("${user-service.admin.password}")
    private String adminPassword;

    private final UserService userService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        userService.createUser(new CreateUserRequest(adminEmail, adminPassword, "","", UserRole.ADMIN))
                .onErrorComplete()
                .subscribe((v) -> log.info("admin <{}> was created", adminEmail));
    }

}
