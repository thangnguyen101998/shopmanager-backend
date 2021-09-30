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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String password;

	@Column(columnDefinition = "nvarchar(250)")
	@NotEmpty(message = "* Full Name not empty")
	@Size(min = 8, max = 100, message = "* Name lengths from 8 to 100")
	private String fullName;
	
	private LocalDate dayOfBirth;
	
	@Column(columnDefinition = "nvarchar(250)")
	private String address;

	private String gender;

	private String phoneNumber;

	private String email;

	private LocalDate createdDate;
	
	private LocalDate modifiedDate;
	
	private boolean deleted;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Bill> bills;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<ImportBill> importBills;

	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role role;

	@Override
	public String toString() {
		return "Users [id=" + id + ", password=" + password + ", fullName=" + fullName + ", dayOfBirth=" + dayOfBirth
				+ ", address=" + address + ", gender=" + gender + ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", deleted=" + deleted + "]";
	}
	
	

}
