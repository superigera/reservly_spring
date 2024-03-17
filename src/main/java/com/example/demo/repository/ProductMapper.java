package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Product;

@Mapper
public interface ProductMapper {

	/**
	 * 商品情報リスト取得
	 * 
	 * @return 商品情報リスト
	 */
	public List<Product> fetchProduct();
}
