package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;
import com.example.demo.service.Product.ProductService;

@CrossOrigin
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	/**
	 * 商品情報取得
	 * 
	 * @return 商品情報
	 */
	@PostMapping("/fetchProduct")
	public ResponseEntity<?> newMemberRegistration() {
		List<Product> resuponseProductList = productService.fetchProduct();
		log.info(resuponseProductList.toString());

		return ResponseEntity.ok().body(resuponseProductList);

	}
}
