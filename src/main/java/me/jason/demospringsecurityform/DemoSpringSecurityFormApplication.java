package me.jason.demospringsecurityform;

import org.hibernate.bytecode.enhance.internal.tracker.NoopCollectionTracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoSpringSecurityFormApplication {

	// 기본적으로 bcrypt encoding 방식이 사용된다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringSecurityFormApplication.class, args);
	}

}
