package com.Forum;

import com.Forum.data.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(UserRepository userRepo, BCryptPasswordEncoder encoder) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				/*
				User user = new User("user" , encoder.encode("password"));
				User admin = new User("admin", encoder.encode("password"));
				admin.admin();
				userRepo.save(user);
				userRepo.save(admin);

				 */
			}
		};
	}

}
