package com.example.demo.service.memberRegistration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.MemberInfo;
import com.example.demo.repository.NewMemberRegistrationMapper;

@Service
public class NewMemberRegistrationServiceImpl implements NewMemberRegistrationService {

	@Autowired
	private NewMemberRegistrationMapper newMemberRegistrationMapper;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 新規会員登録
	 * 
	 * @param memberInfo 会員情報
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Override
	public void newMemberRegistration(MemberInfo memberInfo) throws FileNotFoundException, IOException {

		// 重複チェック
		if (newMemberRegistrationMapper.duplicationCheck(memberInfo.getEmail()) != 0) {
			throw new DuplicationCheckException(memberInfo.getEmail());
		}

		// USER権限を付与
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/main/resources/application.properties"));
		String memberAuthority = properties.getProperty("MEMBER_AUTHORITY_USER");
		memberInfo.setAuthority(memberAuthority);

		// パスワードハッシュ化
		memberInfo.setPassword(bCryptPasswordEncoder.encode(memberInfo.getPassword()));

		newMemberRegistrationMapper.newMemberRegistration(memberInfo);
	}

	public class DuplicationCheckException extends RuntimeException {
		public DuplicationCheckException(String email) {
			super("メールアドレスが重複しています: " + email);
		}
	}

}
