package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.MemberRegistrationInfo;
import com.example.demo.service.memberRegistration.NewMemberRegistrationService;

@CrossOrigin
@RestController
public class MemberRegistrationController {

	@Autowired
	private NewMemberRegistrationService service;

	/**
	 * 新規会員登録
	 * 
	 * @param memberRegistrationInfo 会員情報
	 */
	@PostMapping("/newMemberRegistration")
	public ResponseEntity<?> newMemberRegistration(@RequestBody MemberRegistrationInfo memberRegistrationInfo) {
		try {
			System.out.println(memberRegistrationInfo);
			service.newMemberRegistration(memberRegistrationInfo);
			// その他のビジネスロジック...

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// 一般的なエラーハンドリング
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration failed", e);
		}
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
		return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
	}
}
