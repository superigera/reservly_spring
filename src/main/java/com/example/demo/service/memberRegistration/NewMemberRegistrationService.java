package com.example.demo.service.memberRegistration;

import com.example.demo.model.MemberRegistrationInfo;

public interface NewMemberRegistrationService {

	/**
	 * 新規会員登録
	 * 
	 * @param memberRegistrationInfo 会員情報
	 */
	public void newMemberRegistration(MemberRegistrationInfo memberRegistrationInfo);

//	/**
//	 * 問い合わせ内容確認メール送信
//	 * 
//	 * @param 問い合わせ内容
//	 * @throws URISyntaxException
//	 */
//	public void sendEmail(InquiryInfo InquiryInfo) throws IOException, URISyntaxException;
}
