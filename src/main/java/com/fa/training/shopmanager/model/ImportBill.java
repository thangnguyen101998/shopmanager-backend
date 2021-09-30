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
public class ImportBill {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double total;

	@Column(name = "userId")
	private Long userId;

	@Column(name="supplierId")
	private Long supplierId;

	@ManyToOne
	@JoinColumn(name = "supplierId", insertable = false, updatable = false)
	private Supplier supplier;

	@ManyToOne
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private Users user;

	private LocalDate createDate;

	private LocalDate modifiedDate;

	private boolean deleted;
	
	@OneToMany(mappedBy = "importBill")
	@JsonIgnore
	private List<ImportBillDetails> importBillDetails;
	

	@Override
	public String toString() {
		return "ImportBill [id=" + id + ", total=" + total + ", supplierId=" + supplierId + ", createDate=" + createDate
				+ ", modifiedDate=" + modifiedDate + ", deleted=" + deleted + "]";
	}

}
