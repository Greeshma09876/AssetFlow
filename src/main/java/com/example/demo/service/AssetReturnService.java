
package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Asset;
import com.example.demo.entity.AssetAssignment;
import com.example.demo.entity.AssetReturn;
import com.example.demo.repository.AssetAssignmentRepository;
import com.example.demo.repository.AssetReturnRepository;

@Service
public class AssetReturnService {

    private final AssetReturnRepository returnRepository;
    private final AssetAssignmentRepository assignmentRepository;

    public AssetReturnService(
            AssetReturnRepository returnRepository,
            AssetAssignmentRepository assignmentRepository) {

        this.returnRepository = returnRepository;
        this.assignmentRepository = assignmentRepository;
    }

    // CREATE
    @Transactional
    public AssetReturn saveReturn(
            AssetReturn assetReturn, Long assignmentId) {

        AssetAssignment assignment = assignmentRepository
                .findById(assignmentId)
                .orElseThrow(() ->
                        new RuntimeException("Assignment not found"));

        if (!"Assigned".equalsIgnoreCase(assignment.getStatus())) {
            throw new RuntimeException("Assignment is not active");
        }

        if (returnRepository.findAll().stream()
                .anyMatch(r -> r.getAssignment().getId()
                        .equals(assignmentId))) {
            throw new RuntimeException("Return already recorded");
        }

        Asset asset = assignment.getAsset();

        assignment.setActualReturnDate(assetReturn.getReturnDate());
        assignment.setStatus("Returned");

        updateAssetStatus(asset, assetReturn.getAssetCondition());

        assetReturn.setAssignment(assignment);

        return returnRepository.save(assetReturn);
    }

    // READ ALL
    public List<AssetReturn> getAllReturns() {
        return returnRepository.findAll();
    }

    // READ BY ID
    public AssetReturn getReturnById(Long id) {
        return returnRepository.findById(id).orElse(null);
    }

    // UPDATE
    @Transactional
    public AssetReturn updateReturn(
            Long id, AssetReturn updatedReturn) {

        AssetReturn existingReturn = returnRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Return record not found"));

        existingReturn.setReturnDate(updatedReturn.getReturnDate());
        existingReturn.setAssetCondition(
                updatedReturn.getAssetCondition());
        existingReturn.setAccessoriesReceived(
                updatedReturn.getAccessoriesReceived());
        existingReturn.setRemarks(updatedReturn.getRemarks());

        existingReturn.getAssignment()
                .setActualReturnDate(updatedReturn.getReturnDate());

        updateAssetStatus(
                existingReturn.getAssignment().getAsset(),
                updatedReturn.getAssetCondition());

        return returnRepository.save(existingReturn);
    }

    // DELETE
    @Transactional
    public void deleteReturn(Long id) {

        AssetReturn existingReturn = returnRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Return record not found"));

        AssetAssignment assignment = existingReturn.getAssignment();

        assignment.setStatus("Assigned");
        assignment.setActualReturnDate(null);

        assignment.getAsset().setStatus("Assigned");

        returnRepository.delete(existingReturn);
    }

    // COMMON STATUS UPDATE
    private void updateAssetStatus(
            Asset asset, String condition) {

        if ("Good".equalsIgnoreCase(condition)) {
            asset.setStatus("Available");
        } else {
            asset.setStatus("Under Repair");
        }
    }

    // ACTIVE ASSIGNMENTS
    public List<AssetAssignment> getActiveAssignments() {
        return assignmentRepository.findByStatus("Assigned");
    }
}
