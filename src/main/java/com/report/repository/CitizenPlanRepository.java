package com.report.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.report.binding.CitizenPlan;

public interface CitizenPlanRepository extends JpaRepository<CitizenPlan, Serializable> {
	
	@Query("select distinct(planName) from CitizenPlan")
	public List<String> getPlanNames();
	
	@Query("select distinct(planStatus) from CitizenPlan")
	public List<String> getPlanStatus();
	
	public List<CitizenPlan> findByPlanStatus(String planStatus);
	
	public List<CitizenPlan> findByPlanName(String planName);
	
	public List<CitizenPlan> findByPlanNameAndPlanStatus(String planName, String planStatus);

}
