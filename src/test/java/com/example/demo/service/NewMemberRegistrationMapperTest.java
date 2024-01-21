package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.MemberInfo;
import com.example.demo.repository.NewMemberRegistrationMapper;
import com.example.demo.resources.NewMemberRegistrationMapperTest.TestParams;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@MybatisTest
public class NewMemberRegistrationMapperTest {

	@Autowired
	private NewMemberRegistrationMapper mapper;

	public static Stream<TestParams> provideTestParameters() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		// JSONファイルのパスを指定
		String jsonFilePath = "src/test/java/com/example/demo/resources/NewMemberRegistrationMapperTest/testdata.json";

		// JSONファイルからTestParamsオブジェクトのリストを読み込む
		List<TestParams> testParamList = objectMapper.readValue(Paths.get(jsonFilePath).toFile(),
				new TypeReference<List<TestParams>>() {
				});

		// リストをStreamに変換して返す
		return testParamList.stream();
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("provideTestParameters")
	public void testWithParameters(TestParams params) {

		if (params.testName.contains("重複チェック")) {

			int resultDuplicationCheck = mapper.duplicationCheck(params.email);
			assertEquals(params.duplicationCheckResponse, resultDuplicationCheck);

		} else if (params.testName.contains("新規会員登録")) {

			mapper.newMemberRegistration(params.memberInfoRequest);
			MemberInfo resultMemberInfo = mapper.getMemberInfoByEmail(params.memberInfoRequest.getEmail());
			assertEquals(params.memberInfoResponse.getMemberID(), resultMemberInfo.getMemberID());
			assertEquals(params.memberInfoResponse.getLastName(), resultMemberInfo.getLastName());
			assertEquals(params.memberInfoResponse.getFirstName(), resultMemberInfo.getFirstName());
			assertEquals(params.memberInfoResponse.getEmail(), resultMemberInfo.getEmail());
			assertEquals(params.memberInfoResponse.getPhoneNumber(), resultMemberInfo.getPhoneNumber());
			assertEquals(params.memberInfoResponse.getAge(), resultMemberInfo.getAge());
			assertEquals(params.memberInfoResponse.getGender(), resultMemberInfo.getGender());
			assertEquals(params.memberInfoResponse.getPassword(), resultMemberInfo.getPassword());
			assertEquals(params.memberInfoResponse.getAuthority(), resultMemberInfo.getAuthority());
			assertNotNull(resultMemberInfo.getCreated_at()); // CURRENT_TIMESTAMPの為nullではない事だけ検証する
			assertNull(resultMemberInfo.getUpdate_at());

		}

	}
}
