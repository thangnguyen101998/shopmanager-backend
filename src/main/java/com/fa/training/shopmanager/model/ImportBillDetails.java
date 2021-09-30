package com.fa.training.shopmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class ImportBillDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int quantity;
	
	private double price;
	
	@Column(name="importBillId")
	private Long importBillId;
	
	@ManyToOne
	@JoinColumn(name="importBillId", insertable = false, updatable = false)
	private ImportBill importBill;
	
	@Column(name = "productId")
	private Long productId;
	
	@ManyToOne
	@JoinColumn(name="productId", insertable = false, updatable = false)
	private Product product;

	private boolean deleted;
	
	@Override
	public String toString() {
		return "ImportBillDetails [id=" + id + ", quantity=" + quantity + ", price=" + price + "]";
	}
	
}
