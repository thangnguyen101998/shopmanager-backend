package com.fa.training.shopmanager.validator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fa.training.shopmanager.dto.BillDTO;
import com.fa.training.shopmanager.dto.BillDetailDTO;

public class ValidatorBill {
	
	public static Map<String, String>  validatorBill(BillDTO billDTO) {
		Map<String, String> errors = new HashMap<String, String>();
		String userId = billDTO.getUserId();
		List<BillDetailDTO> billDetailDTOs = billDTO.getBillDetails();
		Date createdDate = billDTO.getCreatedDate();
		Date dateNow = new Date();
		if(Integer.parseInt(userId) == 0) {
			errors.put("userId", "* người bán hàng không được để trống");
		}
		if(billDetailDTOs == null) {
			errors.put("billDetailDTO", "* danh sách sản phẩm nhập vào không tồn tại");
		}else if(billDetailDTOs.size() == 0) {
			errors.put("billDetailDTO", "* danh sách sản phẩm nhập vào không tồn tại");
		}
		if(createdDate == null) {
			errors.put("createdDate", "* ngày bán hàng không được để trống");
		}else if(createdDate.compareTo(dateNow) > 1) {
			errors.put("createDate", "* ngày bán hàng không được lớn hơn ngày hiện tại");
		}
		return errors;
	}

}
