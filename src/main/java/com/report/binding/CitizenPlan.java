package com.report.binding;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CITIZEN_PLAN")
public class CitizenPlan {
	
	@Id
	@GeneratedValue
	private Integer cid;
	private String name;
	private String email;
	private String gender;
	private Long phno;
	private Long ssn;
	private String planName;
	private String planStatus;

}
