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
public class BillDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int quantity;
	
	private double price;
	
	@Column(name="productId")
	private Long productId;
	
	@ManyToOne
	@JoinColumn(name = "productId", insertable = false, updatable = false)
	private Product product;
	
	@Column(name = "billId")
	private Long billId;
	
	@ManyToOne
	@JoinColumn(name="billId", insertable = false, updatable = false)
	private Bill bill;
	
	private boolean deleted;

	@Override
	public String toString() {
		return "BillDetails [id=" + id + ", quantity=" + quantity + "]";
	}
	
	
}
