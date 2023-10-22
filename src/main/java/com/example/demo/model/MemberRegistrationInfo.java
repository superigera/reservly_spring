package com.example.demo.model;

import lombok.Data;

@Data
public class MemberRegistrationInfo {

	private Integer memberID;

	private String lastName;

	private String firstName;

	private String email;

	private String phoneNumber;

	private Integer age;

	private String gender;

	private String password;

	private String authority;

	private String created_at;

	private String update_at;
}
