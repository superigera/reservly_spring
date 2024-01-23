package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.MemberInfo;
import com.example.demo.repository.NewMemberRegistrationMapper;
import com.example.demo.resources.NewMemberRegistrationMapperTest.DuplicationCheckTest.DuplicationCheckTestParams;
import com.example.demo.resources.NewMemberRegistrationMapperTest.NewMemberRegistrationTest.NewMemberRegistrationTestParams;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@MybatisTest
public class NewMemberRegistrationMapperTest {

	@Autowired
	private NewMemberRegistrationMapper mapper;

	final static String FilePath = "src/test/java/com/example/demo/resources/NewMemberRegistrationMapperTest/";

	@Nested
	class NewMemberRegistrationTest {

		public static Stream<NewMemberRegistrationTestParams> provideTestParameters() throws Exception {
			ObjectMapper objectMapper = new ObjectMapper();
			// JSONファイルのパスを指定
			String jsonFilePath = FilePath + "NewMemberRegistrationTest/testdata.json";

			// JSONファイルからTestParamsオブジェクトのリストを読み込む
			List<NewMemberRegistrationTestParams> testParamList = objectMapper.readValue(Paths.get(jsonFilePath).toFile(),
					new TypeReference<List<NewMemberRegistrationTestParams>>() {
					});

			// リストをStreamに変換して返す
			return testParamList.stream();
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("provideTestParameters")
		public void testWithParameters(NewMemberRegistrationTestParams params) {

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

	@Nested
	class DuplicationCheckTest {

		public static Stream<DuplicationCheckTestParams> provideTestParameters() throws Exception {
			ObjectMapper objectMapper = new ObjectMapper();
			// JSONファイルのパスを指定
			String jsonFilePath = FilePath + "DuplicationCheckTest/testdata.json";

			// JSONファイルからTestParamsオブジェクトのリストを読み込む
			List<DuplicationCheckTestParams> testParamList = objectMapper.readValue(Paths.get(jsonFilePath).toFile(),
					new TypeReference<List<DuplicationCheckTestParams>>() {
					});

			// リストをStreamに変換して返す
			return testParamList.stream();
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("provideTestParameters")
		public void testWithParameters(DuplicationCheckTestParams params) {

			int resultDuplicationCheck = mapper.duplicationCheck(params.email);
			assertEquals(params.duplicationCheckResponse, resultDuplicationCheck);
		}
	}
}
