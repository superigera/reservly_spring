package com.example.demo.service.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	/**
	 * 商品情報リスト取得
	 * 
	 * @return 商品情報リスト
	 */
	@Override
	public List<Product> fetchProduct() {
		List<Product> resuponseProductList = productMapper.fetchProduct();

		return resuponseProductList;
	}

}
