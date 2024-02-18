package com.example.demo.resources.NewMemberRegistrationMapperTest.DuplicationCheckTest;

import lombok.Data;

@Data
public class DuplicationCheckTestParams {

	// テスト名
	public String testName;

	// リクエストのメールアドレス
	public String email;

	// 検証結果用の重複チェックのレスポンス
	public int duplicationCheckResponse;

}
