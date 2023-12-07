package com.eazybytes.accounts.entity;

import java.time.LocalDate;

import com.eazybytes.accounts.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Customer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name="native", strategy = "native")
	@Column(name = "customer_id")
	private Long customerId;
	private String name;
	private String email;
	@Column(name = "mobile_number")
	private String mobileNumber;

}
