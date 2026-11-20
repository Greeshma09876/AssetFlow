
package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Asset;
import com.example.demo.entity.AssetAssignment;
import com.example.demo.entity.Employee;

import com.example.demo.repository.AssetAssignmentRepository;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.AssetReturnRepository;
import com.example.demo.repository.EmployeeRepository;

@Service
public class AssetAssignmentService {

	private final AssetAssignmentRepository assignmentRepository;
	private final AssetRepository assetRepository;
	private final EmployeeRepository employeeRepository;
	private final AssetReturnRepository returnRepository;
	
	public AssetAssignmentService(
	        AssetAssignmentRepository assignmentRepository,
	        AssetRepository assetRepository,
	        EmployeeRepository employeeRepository,
	        AssetReturnRepository returnRepository) {

	    this.assignmentRepository = assignmentRepository;
	    this.assetRepository = assetRepository;
	    this.employeeRepository = employeeRepository;
	    this.returnRepository = returnRepository;
	}

    // CREATE - Assign asset
    @Transactional
    public AssetAssignment assignAsset(
            AssetAssignment assignment,
            Long employeeId,
            Long assetId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        if (!"Available".equalsIgnoreCase(asset.getStatus())) {
            throw new RuntimeException("Asset is not available");
        }

        if (assignmentRepository.existsByAssetIdAndStatus(assetId, "Assigned")) {
            throw new RuntimeException("Asset already assigned");
        }

        assignment.setEmployee(employee);
        assignment.setAsset(asset);
        assignment.setStatus("Assigned");

        asset.setStatus("Assigned");

        return assignmentRepository.save(assignment);
    }

    // READ - Get all assignments
    public List<AssetAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // READ - Get assignment by ID
    public AssetAssignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
    }

    // UPDATE - Edit assignment
    @Transactional
    public AssetAssignment updateAssignment(
            Long id,
            Long employeeId,
            Long assetId,
            AssetAssignment updatedAssignment) {

        AssetAssignment existing = getAssignmentById(id);

        if (!"Assigned".equalsIgnoreCase(existing.getStatus())) {
            throw new RuntimeException("Only active assignments can be edited");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Asset newAsset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        Asset oldAsset = existing.getAsset();

        if (!oldAsset.getId().equals(newAsset.getId())) {

            if (!"Available".equalsIgnoreCase(newAsset.getStatus())) {
                throw new RuntimeException("Selected asset is not available");
            }

            oldAsset.setStatus("Available");
            newAsset.setStatus("Assigned");
            existing.setAsset(newAsset);
        }

        existing.setEmployee(employee);
        existing.setAssignmentDate(updatedAssignment.getAssignmentDate());
        existing.setExpectedReturnDate(updatedAssignment.getExpectedReturnDate());
        existing.setRemarks(updatedAssignment.getRemarks());

        return assignmentRepository.save(existing);
    }

 // DELETE - Delete assignment safely
    @Transactional
    public void deleteAssignment(Long id) {

        AssetAssignment assignment = getAssignmentById(id);

        // Check whether a return record exists
        if (returnRepository.existsByAssignmentId(id)) {
        	throw new IllegalStateException(
        		    "Cannot delete this assignment because a return record is linked to it.");
        }

        // Update asset status if assignment is active
        if ("Assigned".equalsIgnoreCase(assignment.getStatus())) {
            assignment.getAsset().setStatus("Available");
        }

        // Delete assignment
        assignmentRepository.delete(assignment);
    }
}
