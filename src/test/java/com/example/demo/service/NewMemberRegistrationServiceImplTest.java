package com.example.demo.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.MemberInfo;
import com.example.demo.repository.NewMemberRegistrationMapper;
import com.example.demo.resources.NewMemberRegistrationServiceImplTest.TestParams;
import com.example.demo.service.memberRegistration.NewMemberRegistrationServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class NewMemberRegistrationServiceImplTest {

	@InjectMocks
	private NewMemberRegistrationServiceImpl service;

	@Mock
	private NewMemberRegistrationMapper newMemberRegistrationMapper;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private Properties properties;

	public static Stream<TestParams> provideTestParameters() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		// JSONファイルのパスを指定
		String jsonFilePath = "src/test/java/com/example/demo/resources/NewMemberRegistrationServiceImplTest/testdata.json";

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

		try {
			when(newMemberRegistrationMapper.duplicationCheck(anyString())).thenReturn(params.duplicationCheckResponse);
			when(bCryptPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");

			if (params.testName.equals("エラー系：環境変数ファイルの取得、ファイルの読み込みエラー")) {
				doThrow(new IOException()).when(properties).load(any(FileInputStream.class));
			} else {
				when(properties.getProperty("MEMBER_AUTHORITY_USER")).thenReturn("USER");
			}

			// 検証対象のメソッドを呼び出す
			service.newMemberRegistration(params.memberInfoRequest);

			// newMemberRegistrationMapperリクエストをキャプチャする
			ArgumentCaptor<MemberInfo> memberInfoArgumentCaptor = ArgumentCaptor.forClass(MemberInfo.class);
			verify(newMemberRegistrationMapper).newMemberRegistration(memberInfoArgumentCaptor.capture());

			// newMemberRegistrationMapperリクエストの整合性を検証する
			MemberInfo capturedMemberInfo = memberInfoArgumentCaptor.getValue();
			assertEquals(params.memberInfoResponse, capturedMemberInfo);

			// 例外が発生するテストケースがオーバーランしないことを確認する
			if (params.resultExceptionName != null) {
				fail("例外が発生していない");
			}
		} catch (Exception e) {
			// キャッチした例外の検証
			if (params.resultExceptionName != null) {

				// 例外が発生するテストケースで想定した例外が発生したことを確認する
				assertEquals(e.getClass().getName(), (params.resultExceptionName));
			} else {
				// 例外が発生しないテストケースで想定外の例外が発生しないことを確認する
				fail("例外が発生している");
			}
		}

	}
}
