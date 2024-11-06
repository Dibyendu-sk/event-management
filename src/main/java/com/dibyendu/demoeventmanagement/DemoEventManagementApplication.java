package com.dibyendu.demoeventmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoAuditing(auditorAwareRef = "auditAwareImplJpa")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImplJpa")
//@EnableJpaRepositories(basePackages = "com.dibyendu.demoeventmanagement.repo.jpa")
//@EnableMongoRepositories(basePackages = "com.dibyendu.demoeventmanagement.repo.mongo")
public class DemoEventManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoEventManagementApplication.class, args);
    }

}
