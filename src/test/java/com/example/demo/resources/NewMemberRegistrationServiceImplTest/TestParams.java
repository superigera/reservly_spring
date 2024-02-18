package com.example.demo.resources.NewMemberRegistrationServiceImplTest;

import com.example.demo.model.MemberInfo;

import lombok.Data;

@Data
public class TestParams {

	// テスト名
	public String testName;

	// リクエストの会員情報
	public MemberInfo memberInfoRequest;

	// 重複チェックのレスポンス
	public int duplicationCheckResponse;

	// 検証結果用の会員情報のレスポンス
	public MemberInfo memberInfoResponse;

	// 結果検証用のエラー名
	public String resultExceptionName;
}
