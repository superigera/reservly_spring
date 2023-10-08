package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.MemberRegistrationInfo;

@Mapper
public interface NewMemberRegistrationMapper {

	/**
	 * 新規会員登録
	 * 
	 * @param memberRegistrationInfo 会員情報
	 */
	public void newMemberRegistration(MemberRegistrationInfo memberRegistrationInfo);
}
