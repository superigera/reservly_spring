package com.example.demo.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.resources.SendEmailServiceImplTest.TestParams;
import com.example.demo.service.SendEmail.SendEmailServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;

@SpringBootTest
public class SendEmailServiceImplTest {

	@InjectMocks
	private SendEmailServiceImpl service;

	@Mock
	private SendGrid sendGrid;

	public static Stream<TestParams> provideTestParameters() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		// JSONファイルのパスを指定
		String jsonFilePath = "src/test/java/com/example/demo/resources/SendEmailServiceImplTest/testdata.json";

		// JSONファイルからTestParamsオブジェクトのリストを読み込む
		List<TestParams> testParamList = objectMapper.readValue(Paths.get(jsonFilePath).toFile(),
				new TypeReference<List<TestParams>>() {
				});

		// リストをStreamに変換して返す
		return testParamList.stream();
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("provideTestParameters")
	public void testWithParameters(TestParams params) throws IOException {

		when(sendGrid.api(any(Request.class))).thenReturn(params.sendGridResponse);

		service.sendMemberRegistrationEmail(params.memberInfoRequest);

		// モックの SendGrid が適切に呼び出されたか検証
		if (!params.testName.contains("サーバー未到達")) {
			verify(sendGrid, times(1)).api(any(Request.class));
		}

	}
}
