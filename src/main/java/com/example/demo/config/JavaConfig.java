package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sendgrid.SendGrid;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class JavaConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SendGrid sendGrid() {
		Dotenv dotenv = Dotenv.load();
		return new SendGrid(dotenv.get("MAIL_SEND_KEY"));
	}

}
