package com.example.demo.resources.ProductServiceInplTest;

import java.util.List;

import com.example.demo.model.Product;

import lombok.Data;

@Data
public class TestParams {

	// テスト名
	public String testName;

	// 結果検証用の商品情報リスト
	public List<Product> productListResponse;

}
