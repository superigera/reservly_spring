package com.example.demo.model;

import lombok.Data;

@Data
public class Product {

	private Integer productID;

	private String productName;

	private String productDescription;

	private String productImage;

	private Integer productPrice;

	private Integer productQuantity;

	private Integer productMaxQuantity;

	private String productReleaseDate;

	private String productCategoryName;

	private String created_at;

	private String update_at;
}
