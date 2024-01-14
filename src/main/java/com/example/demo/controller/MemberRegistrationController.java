package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.MemberInfo;
import com.example.demo.service.SendEmail.SendEmailService;
import com.example.demo.service.memberRegistration.NewMemberRegistrationService;
import com.example.demo.service.memberRegistration.NewMemberRegistrationServiceImpl.DuplicationCheckException;

@CrossOrigin
@RestController
public class MemberRegistrationController {

	@Autowired
	private NewMemberRegistrationService newMemberRegistrationService;

	@Autowired
	private SendEmailService sendEmailService;

	private static final Logger log = LoggerFactory.getLogger(MemberRegistrationController.class);

	/**
	 * 新規会員登録
	 * 
	 * @param memberInfo 会員情報
	 */
	@PostMapping("/newMemberRegistration")
	public ResponseEntity<?> newMemberRegistration(@RequestBody MemberInfo memberInfo) {
		try {
			newMemberRegistrationService.newMemberRegistration(memberInfo);
			sendEmailService.sendMemberRegistrationEmail(memberInfo);

			return ResponseEntity.ok().build();
		} catch (DuplicationCheckException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration failed", e);
		}
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
		return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
	}
}
