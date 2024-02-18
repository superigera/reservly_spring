package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.MemberInfo;

@Mapper
public interface NewMemberRegistrationMapper {

	/**
	 * 新規会員登録
	 * 
	 * @param memberInfo 会員情報
	 */
	public void newMemberRegistration(MemberInfo memberInfo);

	/**
	 * 新規会員登録重複チェック
	 * 
	 * @param email メールアドレス
	 */
	public int duplicationCheck(String email);

	/**
	 * 会員情報取得
	 * 
	 * @param email メールアドレス
	 */
	public MemberInfo getMemberInfoByEmail(String email);
}
