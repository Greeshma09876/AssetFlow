package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@Controller
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	public String getAllEmployees(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "employees";

		// employeeService.getAllEmployees() - get all employee records from the service
		// model.addAttribute - sends those records to thymeleaf page
		// return "employees" - opens employees.html from the template folder
	}

	@GetMapping("/employees/add")
//When the browser requests /employees/add, this method executes.
	public String showAddEmployeeForm(Model model) {

		model.addAttribute("employee", new Employee()); // Creates an empty Employee object.
		// Sends that empty Employee object to the HTML page.

		return "add-employee";
		// Opens add-employee.html.
	}

	@PostMapping("/employees/save")
	// This receives the submitted employee details when the user clicks Save
	// Employee.
	public String saveEmployee(@ModelAttribute Employee employee) {
		employeeService.saveEmployee(employee);
		// Sends the Employee object to the Service layer.
		return "redirect:/employees";
		// After saving, redirects the browser to the employee list page.

	}

	@GetMapping("/employees/edit/{id}")
	public String editEmployee(@PathVariable Long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "edit-employee";
	}

	@PostMapping("/employees/update/{id}")
	public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {

		employeeService.updateEmployee(id, employee);

		return "redirect:/employees";
	}

	@GetMapping("/employees/delete/{id}")
	public String deleteEmployee(@PathVariable Long id) {

		employeeService.deleteEmployee(id);

		return "redirect:/employees";
	}
}
