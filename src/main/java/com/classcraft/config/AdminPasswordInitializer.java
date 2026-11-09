package com.classcraft.config;

import com.classcraft.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * If ADMIN_PASSWORD is set, makes the seeded "admin" user use that password.
 * The plaintext lives only in the environment (.env), never in source control.
 */
@Slf4j
@Component
public class AdminPasswordInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminPassword;

    public AdminPasswordInitializer(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder,
                                    @Value("${app.admin.password:}") String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (adminPassword.isBlank()) {
            return;
        }
        userRepository.findByUsername("admin").ifPresentOrElse(user -> {
            if (!passwordEncoder.matches(adminPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(adminPassword));
                userRepository.save(user);
                log.info("Admin password updated from ADMIN_PASSWORD");
            }
        }, () -> log.warn("ADMIN_PASSWORD is set but no 'admin' user exists"));
    }
}
