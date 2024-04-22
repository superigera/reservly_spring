package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductMapper;
import com.example.demo.resources.ProductServiceImplTest.TestParams;
import com.example.demo.service.Product.ProductServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class ProductServiceImplTest {

	@InjectMocks
	private ProductServiceImpl service;

	@Mock
	private ProductMapper productMapper;

	public static Stream<TestParams> provideTestParameters() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		// JSONファイルのパスを指定
		String jsonFilePath = "src/test/java/com/example/demo/resources/ProductServiceImplTest/testdata.json";

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

		when(productMapper.fetchProduct()).thenReturn(params.productListResponse);

		// 検証対象のメソッドを呼び出す
		List<Product> resulProduct = service.fetchProduct();
		assertEquals(params.productListResponse.size(), resulProduct.size(), "リストのサイズが期待値と異なります");
		assertEquals(params.productListResponse, resulProduct, "リストの内容が期待値と異なります");

	}
}
