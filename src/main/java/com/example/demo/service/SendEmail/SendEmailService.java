package com.example.demo.service.SendEmail;

import com.example.demo.model.MemberInfo;

public interface SendEmailService {

	/**
	 * 新規会員登録完了メール
	 * 
	 * @param 会員情報
	 */
	public void sendMemberRegistrationEmail(MemberInfo memberInfo);
}
