package com.example.demo.resources.NewMemberRegistrationMapperTest;

import com.example.demo.model.MemberInfo;

import lombok.Data;

@Data
public class TestParams {

	// テスト名
	public String testName;

	// リクエストの会員情報
	public MemberInfo memberInfoRequest;

	// 検証結果用の会員情報のレスポンス
	public MemberInfo memberInfoResponse;

	// リクエストのメールアドレス
	public String email;

	// 検証結果用の会員情報のレスポンス
	public int duplicationCheckResponse;

}
