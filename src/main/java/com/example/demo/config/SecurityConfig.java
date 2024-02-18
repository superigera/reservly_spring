package com.example.demo.config;

import static org.springframework.security.config.Customizer.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(withDefaults()).csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/login"), // TODO
				new AntPathRequestMatcher("/h2-console/**"), new AntPathRequestMatcher("/loginSuccess"),
				new AntPathRequestMatcher("/newMemberRegistration")))
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers(new AntPathRequestMatcher("/csrf-token")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/newMemberRegistration/comfirm")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/loginSuccess")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/newMemberRegistration")).permitAll())
				.formLogin(form -> form.loginProcessingUrl("/login")
						.successHandler((request, response, authentication) -> {
							Object principal = authentication.getPrincipal();
							String authority = authentication.getAuthorities().isEmpty() ? ""
									: authentication.getAuthorities().iterator().next().getAuthority();

							Map<String, String> data = new HashMap<>();
							if (principal instanceof UserDetails) {
								UserDetails userDetails = (UserDetails) principal;
								data.put("email", userDetails.getUsername());
								data.put("authority", authority);
							}

							response.setStatus(HttpStatus.OK.value());
							response.setContentType("application/json;charset=UTF-8");
							new ObjectMapper().writeValue(response.getOutputStream(), data);
						}).failureHandler((request, response, exception) -> {
							response.setStatus(HttpStatus.UNAUTHORIZED.value());
						}))
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // フロントエンドのオリジンを許可
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
