package com.fa.training.shopmanager.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "nvarchar(250)")
	@NotBlank(message = "* tên nhà cung cấp không được để trống")
	@Size(min = 2, max = 30,message = "* tên nhà cung cấp có độ dài từ 2 đến 30 kí tự")
	private String supplierName;
	
	@NotBlank(message = "* số điện thoại không được để trống")
	@Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{9})\\b",
	message = "* số điện thoại không hợp lệ (ví dụ: 0988888888)")
	private String phoneNumber;
	
	@NotBlank(message = "* email nhà cung cấp không được để trống")
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "* email không hợp lệ (ví dụ: test@gmail.com)")
	private String email;
	
	@Column(columnDefinition = "nvarchar(250)")
	@NotBlank(message = "* địa chỉ không được để trống")
	private String address;
	
	private LocalDate createdDate;
	
	private LocalDate modifiedDate;
	
	private boolean deleted;
	
	@OneToMany(mappedBy = "supplier")
	@JsonIgnore
	private List<ImportBill> importBills;

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", supplierName=" + supplierName + ", phoneNumber=" + phoneNumber + ", email="
				+ email + ", address=" + address + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", deleted=" + deleted + "]";
	}
	
}
