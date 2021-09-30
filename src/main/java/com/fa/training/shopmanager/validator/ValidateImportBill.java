package com.fa.training.shopmanager.validator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fa.training.shopmanager.dto.ImportBillDTO;
import com.fa.training.shopmanager.dto.ImportBillDetailDTO;
public class ValidateImportBill {
	
	public static Map<String, String>  validatorProduct(ImportBillDTO importBillDTO) {
		Map<String, String> errors = new HashMap<String, String>();
		String userId = importBillDTO.getUserId();
		String supplierId = importBillDTO.getSupplierId();
		List<ImportBillDetailDTO> importBillDetailDTOs = importBillDTO.getImportBillDetails();
		Date createdDate = importBillDTO.getCreatedDate();
		Date dateNow = new Date();
		if(Integer.parseInt(userId) == 0) {
			errors.put("userId", "* người nhập hàng không được để trống");
		}
		if(Integer.parseInt(supplierId) == 0) {
			errors.put("supplierId", "* nhà cung cấp không được để trống");
		}
		if(importBillDetailDTOs.size() == 0) {
			errors.put("importBillDetailDTOs", "* danh sách sản phẩm nhập vào không tồn tại");
		}
		if(createdDate == null) {
			errors.put("createdDate", "* ngày nhập vào không được để trống");
		}else if(createdDate.compareTo(dateNow) > 1) {
			errors.put("createDate", "* ngày nhập vào không được lớn hơn ngày hiện tại");
		}
		return errors;
	}
}
