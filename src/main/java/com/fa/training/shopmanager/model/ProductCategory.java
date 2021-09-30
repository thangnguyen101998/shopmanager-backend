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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class ProductCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "nvarchar(250)")
	@NotBlank(message = "* tên loại sản phẩm không được để trống")
	@Size(min = 2, max = 30,message = "* tên loại sản phẩm có độ dài từ 2 đến 30 kí tự")
	private String name;
	
	@Column(columnDefinition = "ntext")
	@NotBlank(message ="* thông tin loại sản phẩm không được để trống")
	private String description;
	
	private LocalDate createdDate;
	
	private LocalDate modifiedDate;
	
	private boolean deleted;
	
	@OneToMany(mappedBy = "productCategory")
	@JsonIgnore
	private List<Product> products;

	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", name=" + name + ", description=" + description + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", deleted=" + deleted + "]";
	}
	
}