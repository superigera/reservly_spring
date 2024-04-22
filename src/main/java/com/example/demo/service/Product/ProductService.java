package com.example.demo.service.Product;

import java.util.List;

import com.example.demo.model.Product;

public interface ProductService {

	/**
	 * 商品情報リスト取得
	 * 
	 * @return 商品情報リスト
	 */
	public List<Product> fetchProduct();
}
