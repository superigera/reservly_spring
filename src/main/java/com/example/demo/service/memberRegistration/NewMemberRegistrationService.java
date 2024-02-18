package com.example.demo.service.memberRegistration;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.demo.model.MemberInfo;

public interface NewMemberRegistrationService {

	/**
	 * 新規会員登録
	 * 
	 * @param memberInfo 会員情報
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void newMemberRegistration(MemberInfo memberInfo) throws FileNotFoundException, IOException;
}
