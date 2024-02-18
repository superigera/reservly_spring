package com.example.demo.resources.SendEmailServiceImplTest;

import com.example.demo.model.MemberInfo;
import com.sendgrid.Response;

import lombok.Data;

@Data
public class TestParams {

	// テスト名
	public String testName;

	// リクエストの会員情報
	public MemberInfo memberInfoRequest;

	// SendGridのレスポンス
	public Response sendGridResponse;
}
