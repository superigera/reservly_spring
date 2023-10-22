package com.example.demo.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			String sql = "SELECT * FROM members WHERE email = ?";
			Map<String, Object> map = jdbcTemplate.queryForMap(sql, email);
			String password = (String) map.get("password");
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority((String) map.get("authority")));
			return new UserDetailsImpl(email, password, authorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("user not found.", e);
		}
	}
}
