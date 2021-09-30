package com.fa.training.shopmanager.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "nvarchar(250)")
	private String name;

	@Column(columnDefinition = "ntext")
	private String description;

	private String imageUrl;

	private int quantity;

	private double price;

	@Column(name = "productCategoryId")
	private Long productCategoryId;

	@ManyToOne
	@JoinColumn(name = "productCategoryId", insertable = false, updatable = false)
	private ProductCategory productCategory;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<ImportBillDetails> importBillDetails;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<BillDetails> billDetails;

	private LocalDate createdDate;

	private LocalDate modifiedDate;

	private boolean deleted;

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", imageUrl=" + imageUrl
				+ ", quantity=" + quantity + ", price=" + price + ", productCategoryId=" + productCategoryId
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", deleted=" + deleted + "]";
	}

}
