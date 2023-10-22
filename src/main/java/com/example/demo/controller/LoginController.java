package com.example.demo.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MemberRegistrationInfo;

@CrossOrigin
@RestController
public class LoginController {
//	/**
//	 * ログイン
//	 * 
//	 * @param memberRegistrationInfo 会員情報
//	 */
//	@PostMapping("/login")
//	public String newMemberRegistration(String name, String password) {
//		System.out.println("loginしようとした");
//		return "login";
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

	/**
	 * csrftoken
	 * 
	 * @param memberRegistrationInfo 会員情報
	 */
	@GetMapping("/loginSuccess")
	public ResponseEntity<MemberRegistrationInfo> loginSuccess(Principal principal) {
		System.out.println("--------------------------------------------------");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		System.out.println(username);

		MemberRegistrationInfo member = new MemberRegistrationInfo();
//		member.setEmail(principal.getName());
		return ResponseEntity.ok(member);
	}
}
