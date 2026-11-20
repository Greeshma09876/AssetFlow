
package com.example.demo.Controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.AssetAssignment;

import com.example.demo.service.AssetAssignmentService;
import com.example.demo.service.AssetService;
import com.example.demo.service.EmployeeService;

@Controller
public class AssetAssignmentController {

    private final AssetAssignmentService assignmentService;
    private final AssetService assetService;
    private final EmployeeService employeeService;

    public AssetAssignmentController(
            AssetAssignmentService assignmentService,
            AssetService assetService,
            EmployeeService employeeService) {

        this.assignmentService = assignmentService;
        this.assetService = assetService;
        this.employeeService = employeeService;
    }

    // READ - Display all assignments
    @GetMapping("/assignments")
    public String getAllAssignments(Model model) {

        model.addAttribute(
                "assignments",
                assignmentService.getAllAssignments());

        return "assignments";
    }

    // CREATE - Display add form
    @GetMapping("/assignments/add")
    public String showAssignmentForm(Model model) {

        model.addAttribute("assignment", new AssetAssignment());

        model.addAttribute(
                "employees",
                employeeService.getAllEmployees());

        model.addAttribute(
                "assets",
                assetService.getAllAssets());

        model.addAttribute("today", LocalDate.now());

        return "add-assignment";
    }

    // CREATE - Save assignment
    @PostMapping("/assignments/save")
    public String saveAssignment(
            @ModelAttribute AssetAssignment assignment,
            @RequestParam Long employeeId,
            @RequestParam Long assetId) {

        assignmentService.assignAsset(
                assignment,
                employeeId,
                assetId);

        return "redirect:/assignments";
    }

    // UPDATE - Display edit form
    @GetMapping("/assignments/edit/{id}")
    public String editAssignment(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "assignment",
                assignmentService.getAssignmentById(id));

        model.addAttribute(
                "employees",
                employeeService.getAllEmployees());

        model.addAttribute(
                "assets",
                assetService.getAllAssets());

        return "edit-assignment";
    }

    // UPDATE - Save changes
    @PostMapping("/assignments/update/{id}")
    public String updateAssignment(
            @PathVariable Long id,
            @ModelAttribute AssetAssignment assignment,
            @RequestParam Long employeeId,
            @RequestParam Long assetId) {

        assignmentService.updateAssignment(
                id,
                employeeId,
                assetId,
                assignment);

        return "redirect:/assignments";
    }

 // DELETE - Delete assignment
    @PostMapping("/assignments/delete/{id}")
    public String deleteAssignment(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            assignmentService.deleteAssignment(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Assignment deleted successfully.");

        } catch (IllegalStateException ex) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage());
        }

        return "redirect:/assignments";
    }
}
