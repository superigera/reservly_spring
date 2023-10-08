package com.example.demo.service.memberRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MemberRegistrationInfo;
import com.example.demo.repository.NewMemberRegistrationMapper;

@Service
public class NewMemberRegistrationServiceImpl implements NewMemberRegistrationService {

	@Autowired
	private NewMemberRegistrationMapper inquiryMapper;

	/**
	 * 新規会員登録
	 * 
	 * @param memberRegistrationInfo 会員情報
	 */
	@Override
	public void newMemberRegistration(MemberRegistrationInfo memberRegistrationInfo) {

		inquiryMapper.newMemberRegistration(memberRegistrationInfo);
	}

}
