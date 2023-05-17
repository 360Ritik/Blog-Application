package com.example.demo.blogapplication;

import com.example.demo.blogapplication.auditing.SpringSecurityAuditorAware;
import com.example.demo.blogapplication.model.Role;
import com.example.demo.blogapplication.repository.RoleRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditor")
@OpenAPIDefinition(
        info = @Info(
                title = "Spring boot Rest API's",
                description = "spring boot blog application description",
                version = "v1.0",
                contact = @Contact(
                        name = "ritik kumar",
                        email = "ritik@gmail.com",
                        url = "localhost:8080/get/info"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "localhost:8080/get/licence"
                )
        )
)
public class BlogApplication implements CommandLineRunner {


    final RoleRepository repository;

    @Autowired
    public BlogApplication(RoleRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AuditorAware<String> auditor() {
        return new SpringSecurityAuditorAware();
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<Role> adminRole = repository.findByName("ROLE_ADMIN");
        Optional<Role> userRole = repository.findByName("ROLE_ADMIN");
        if (adminRole.isEmpty()) {
            Role admin = new Role();
            admin.setName("ROLE_ADMIN");
            repository.save(admin);
        }

        if (userRole.isEmpty()) {


            Role user = new Role();
            user.setName("ROLE_USER");
            repository.save(user);
        }
    }
}
