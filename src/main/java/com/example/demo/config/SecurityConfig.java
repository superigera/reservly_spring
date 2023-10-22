package com.example.demo.config;

import static org.springframework.security.config.Customizer.*;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
//		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user")
//				.password(passwordEncoder().encode("test")).roles("USER");
//		auth.userDetailsService(userDetailsService);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//		auth.authenticationProvider(authenticationProvider());
	}

//	@Autowired
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService);
//		provider.setPasswordEncoder(passwordEncoder());
//		return provider;
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(withDefaults()).csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/login"), // TODO
				new AntPathRequestMatcher("/h2-console/**"), new AntPathRequestMatcher("/loginSuccess")))
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers(new AntPathRequestMatcher("/csrf-token")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/newMemberRegistration/comfirm")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/loginSuccess")).permitAll()
//						.requestMatchers(new AntPathRequestMatcher("/loginSuccess")).authenticated()

						.requestMatchers(new AntPathRequestMatcher("/login")).permitAll().anyRequest().authenticated())
				.formLogin(form -> form.loginProcessingUrl("/login")
						.successHandler((request, response, authentication) -> {
							response.sendRedirect("/loginSuccess");
						}).failureHandler((request, response, exception) -> {
							response.setStatus(HttpStatus.UNAUTHORIZED.value());
						}))
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		;

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
