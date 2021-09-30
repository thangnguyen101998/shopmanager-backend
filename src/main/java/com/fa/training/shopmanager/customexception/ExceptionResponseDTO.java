package com.fa.training.shopmanager.customexception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponseDTO {

	private String message;
	private String code;
}
