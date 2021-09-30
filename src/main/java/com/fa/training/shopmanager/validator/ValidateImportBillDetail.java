package com.fa.training.shopmanager.validator;

import java.util.HashMap;

import java.util.Map;

import com.fa.training.shopmanager.dto.ImportBillDetailDTO;

public class ValidateImportBillDetail {
	
	public static Map<String, String>  validatorProduct(ImportBillDetailDTO importBillDetailDTO) {
		Map<String, String> errors = new HashMap<String, String>();
		int quantity = importBillDetailDTO.getQuantity();
		double price = importBillDetailDTO.getPrice();
		if(quantity < 1 || quantity > 1000000000) {
			errors.put("quantity", "* số lượng phải từ 1 đến 1000000000");
		}
		if(price < 1 || price > 1000000000) {
			errors.put("price", " * giá nhập vào phải từ 1 đến 1000000000");
		}
		return errors;
	}

}
