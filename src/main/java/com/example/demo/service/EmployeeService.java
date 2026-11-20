package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	//By using constructor injection spring passes the repo object through the constructor
	//CI- when spring creates EmployeeService it automatically supplies the EmployeeRepository object(employeeRepository)
	private final EmployeeRepository employeeRepository;
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository=employeeRepository;
		
		//this.employeeRepository(instance variable)
		//employeeRepository(parameter received through the constructor
		//= assign the received repository object to the instance variable 
	}
	
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
//saveEmployee receives employee details from controller and this (save) method gives the details to repository.Then repository saves to the database
	}
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
		
//retrieves all records from the database
		
	}
	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id).orElse(null);
		
	}
	
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}
	
	public Employee updateEmployee(Long id, Employee updateEmployee) {
		
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		
		if(optionalEmployee.isPresent()) {
			
			Employee existingEmployee = optionalEmployee.get();
			
			
			existingEmployee.setEmployeeCode(updateEmployee.getEmployeeCode());
			existingEmployee.setName(updateEmployee.getName());
			existingEmployee.setEmail(updateEmployee.getEmail());
			existingEmployee.setPhone(updateEmployee.getPhone());
			existingEmployee.setDepartment(updateEmployee.getDepartment());
			existingEmployee.setDesignation(updateEmployee.getDesignation());
			existingEmployee.setStatus(updateEmployee.getStatus());
			
			return employeeRepository.save(existingEmployee);
		}
		
		return null;
	}	

}
