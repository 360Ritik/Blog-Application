package com.example.demo.blogapplication;

import com.example.demo.blogapplication.auditing.SpringSecurityAuditorAware;
import com.example.demo.blogapplication.model.Role;
import com.example.demo.blogapplication.model.User;
import com.example.demo.blogapplication.repository.RoleRepository;
import com.example.demo.blogapplication.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.unlogged.Unlogged;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


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

   final UserRepository userRepository;
    final RoleRepository repository;

 final PasswordEncoder passwordEncoder;
    @Autowired
    public BlogApplication(UserRepository userRepository, RoleRepository repository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Unlogged
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

        Optional<Role> userRole = repository.findByName("ROLE_ADMIN");
        Optional<User>admin1 = userRepository.findById(1L);

        if (userRole.isEmpty()) {
            Role user = new Role();
            user.setName("ROLE_USER");
            repository.save(user);
        }
        if(admin1.isEmpty()){
            User user=new User();
            Set<Role> roles = new HashSet<>();
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roles.add(role);
            user.setName("Admin");
            user.setEmail("Admin111@gmail.com");
            user.setPassword(passwordEncoder.encode("Admin@123"));
            user.setRoles(roles);
            userRepository.save(user);
        }


    }
}
