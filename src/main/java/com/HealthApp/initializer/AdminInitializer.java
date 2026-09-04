package com.HealthApp.initializer;

import com.HealthApp.model.Admin;
import com.HealthApp.model.Role;
import com.HealthApp.repo.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);


    @Override
    public void run(String... args) {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            throw new IllegalStateException("Bootstrap admin credentials cannot be blank");
        }
        Admin admin = repo.findByEmail(adminEmail).orElseGet(
                () -> {
                    Admin newAdmin = new Admin();
                    newAdmin.setEmail(adminEmail);
                    newAdmin.setPassword(passwordEncoder.encode(adminPassword));
                    newAdmin.setRole(Role.ADMIN);
                    newAdmin.setOwner(true);
                    repo.save(newAdmin);
                    logger.info("Owner admin created successfully");
                    return newAdmin;
                }
        );
        if (admin.isOwner() || admin.getRole() != Role.ADMIN) {
            if (!admin.isOwner()) {
                logger.warn("Bootstrap admin account is not marked as owner");
            }
            if (admin.getRole() != Role.ADMIN) {
                logger.warn("Bootstrap admin account does not have the ADMIN role.");
            }
        }
    }
}
