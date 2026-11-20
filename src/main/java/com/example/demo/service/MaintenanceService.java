
package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Asset;
import com.example.demo.entity.MaintenanceRecord;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.MaintenanceRepository;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final AssetRepository assetRepository;

    public MaintenanceService(
            MaintenanceRepository maintenanceRepository,
            AssetRepository assetRepository) {

        this.maintenanceRepository = maintenanceRepository;
        this.assetRepository = assetRepository;
    }

    private boolean hasActiveMaintenance(Long assetId) {
        return maintenanceRepository
                .existsByAssetIdAndMaintenanceStatusIn(
                        assetId,
                        Arrays.asList("Pending", "In Progress"));
    }

    private void updateAssetStatus(Long assetId) {

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() ->
                        new RuntimeException("Asset not found"));

        if (hasActiveMaintenance(assetId)) {
            asset.setStatus("Under Repair");
        } else {
            asset.setStatus("Available");
        }
    }

    // CREATE
    @Transactional
    public MaintenanceRecord saveMaintenance(
            MaintenanceRecord record, Long assetId) {

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() ->
                        new RuntimeException("Asset not found"));

        if ("Assigned".equalsIgnoreCase(asset.getStatus())
                || "Retired".equalsIgnoreCase(asset.getStatus())
                || "Lost".equalsIgnoreCase(asset.getStatus())) {
            throw new RuntimeException(
                    "This asset cannot be sent for maintenance");
        }

        record.setAsset(asset);

        if (record.getMaintenanceStatus() == null
                || record.getMaintenanceStatus().isBlank()) {
            record.setMaintenanceStatus("Pending");
        }

        if (record.getReportedDate() == null) {
            record.setReportedDate(
                    java.time.LocalDate.now());
        }

        MaintenanceRecord saved =
                maintenanceRepository.save(record);

        asset.setStatus("Under Repair");

        return saved;
    }

    // READ ALL
    public List<MaintenanceRecord> getAllMaintenance() {
        return maintenanceRepository.findAll();
    }

    // READ BY ID
    public MaintenanceRecord getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id).orElse(null);
    }

    // UPDATE
    @Transactional
    public MaintenanceRecord updateMaintenance(
            Long id, MaintenanceRecord updatedRecord) {

        MaintenanceRecord existing = maintenanceRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Maintenance record not found"));

        Long assetId = existing.getAsset().getId();

        existing.setIssueDescription(
                updatedRecord.getIssueDescription());

        existing.setReportedDate(
                updatedRecord.getReportedDate());

        existing.setRepairDate(
                updatedRecord.getRepairDate());

        existing.setTechnicianName(
                updatedRecord.getTechnicianName());

        existing.setRepairCost(
                updatedRecord.getRepairCost());

        existing.setMaintenanceStatus(
                updatedRecord.getMaintenanceStatus());

        existing.setRemarks(
                updatedRecord.getRemarks());

        MaintenanceRecord saved =
                maintenanceRepository.save(existing);

        updateAssetStatus(assetId);

        return saved;
    }

    // DELETE
    @Transactional
    public void deleteMaintenance(Long id) {

        MaintenanceRecord existing = maintenanceRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Maintenance record not found"));

        Long assetId = existing.getAsset().getId();

        maintenanceRepository.delete(existing);

        updateAssetStatus(assetId);
    }
}
