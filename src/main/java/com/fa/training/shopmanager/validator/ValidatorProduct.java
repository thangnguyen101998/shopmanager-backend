package com.fa.training.shopmanager.validator;

import java.util.HashMap;
import java.util.Map;

import com.fa.training.shopmanager.model.Product;

public class ValidatorProduct {
	
	public static Map<String, String>  validatorProduct(Product product) {
		Map<String, String> errors = new HashMap<String, String>();
		String name = product.getName();
		String description = product.getDescription();
		String imageUrl = product.getImageUrl();
		double price = product.getPrice();
		Long productCategoryId = product.getProductCategoryId();
		if(name.length() == 0) {
			errors.put("name", "* tên sản phẩm không được để trống");
		}
		if(name.length() < 10 || name.length() > 50) {
			errors.put("name", "* tên sản phẩm phải có độ dài từ 10 đến 50 ký tự");
		}
		if(description.length() < 1) {
			errors.put("description", "* thông tin sản phẩm không được để trống");
		}
		if(imageUrl.length() < 1) {
			errors.put("imageUrl", "* ảnh sản phẩm không được để trống");
		}
		if(price < 1) {
			errors.put("price", "* giá bán phải lớn hơn 1");
		}
		if(price > 1000000000) {
			errors.put("price", "* giá bán phải nhỏ hơn 1000000000");
		}
		if(productCategoryId < 1) {
			errors.put("productCategoryId", "* phải chọn loại sản phẩm");
		}
		return errors;
	}
}

