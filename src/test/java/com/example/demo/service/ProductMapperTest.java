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

import com.example.demo.model.Product;
import com.example.demo.repository.ProductMapper;
import com.example.demo.resources.ProductMapperTest.TestParams;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@MybatisTest
public class ProductMapperTest {

	@Autowired
	private ProductMapper mapper;

	final static String FilePath = "src/test/java/com/example/demo/resources/ProductMapperTest/";

	@Nested
	class NewMemberRegistrationTest {

		public static Stream<TestParams> provideTestParameters() throws Exception {
			ObjectMapper objectMapper = new ObjectMapper();
			// JSONファイルのパスを指定
			String jsonFilePath = FilePath + "testdata.json";

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

			List<Product> resulProduct = mapper.fetchProduct();
			assertEquals(params.productListResponse.size(), resulProduct.size(), "リストのサイズが期待値と異なります");
			assertEquals(params.productListResponse, resulProduct, "リストの内容が期待値と異なります");
		}
	}
}
