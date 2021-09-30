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
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "userId")
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name="userId", insertable = false, updatable = false)
	private Users user;
	
	private LocalDate saleDate;
	
	private double total;
	
	private LocalDate createdDate;
	
	private LocalDate modifiedDate;
	
	private boolean deleted;
	
	@OneToMany(mappedBy = "bill")
	@JsonIgnore
	List<BillDetails> billDetails;

	@Override
	public String toString() {
		return "Bill [id=" + id + ", saleDate=" + saleDate + ", total=" + total + "]";
	}
	
	
	
	
}
