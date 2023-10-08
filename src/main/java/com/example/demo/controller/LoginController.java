package com.example.demo.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class LoginController {
	/**
	 * ログイン
	 * 
	 * @param memberRegistrationInfo 会員情報
	 */
	@PostMapping("/login")
	public String newMemberRegistration(String name, String password) {
		System.out.println("loginしようとした");
		return "login";
	}
//	@PostMapping("/login")
//	public void loginEndpoint(HttpServletRequest request) {
//		Enumeration<String> headerNames = request.getHeaderNames();
//		while (headerNames.hasMoreElements()) {
//			String headerName = headerNames.nextElement();
//			String headerValue = request.getHeader(headerName);
//			System.out.println(headerName + ": " + headerValue);
//		}
//	}

	/**
	 * csrftoken
	 * 
	 * @param memberRegistrationInfo 会員情報
	 */
	@GetMapping("/csrf-token")
	public String getCsrfToken(CsrfToken token) {
		System.out.println("token取得");
		return token.getToken();
	}
}
