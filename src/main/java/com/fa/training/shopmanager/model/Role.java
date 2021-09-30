package com.fa.training.shopmanager.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private LocalDate createdDate;
	
	private LocalDate modifiedDate;
	
	private boolean status;
	
	@OneToMany(mappedBy = "role")
	@JsonIgnore
	public Set<Users> users;

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ ", status=" + status + "]";
	}

	
}
