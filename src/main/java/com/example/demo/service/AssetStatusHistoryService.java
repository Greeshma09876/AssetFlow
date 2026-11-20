
package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Asset;
import com.example.demo.entity.AssetStatusHistory;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.AssetStatusHistoryRepository;

@Service
public class AssetStatusHistoryService {

    private final AssetStatusHistoryRepository historyRepository;
    private final AssetRepository assetRepository;

    public AssetStatusHistoryService(
            AssetStatusHistoryRepository historyRepository,
            AssetRepository assetRepository) {

        this.historyRepository = historyRepository;
        this.assetRepository = assetRepository;
    }

    // CREATE HISTORY AND UPDATE ASSET STATUS
    @Transactional
    public AssetStatusHistory saveHistory(
            AssetStatusHistory history, Long assetId) {

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() ->
                        new RuntimeException("Asset not found"));

        String previousStatus = asset.getStatus();
        String newStatus = history.getNewStatus();

        if (newStatus == null || newStatus.isBlank()) {
            throw new RuntimeException("New status is required");
        }

        // Update actual asset
        asset.setStatus(newStatus);
        assetRepository.save(asset);

        // Create history record
        history.setAsset(asset);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setChangedAt(LocalDateTime.now());

        AssetStatusHistory savedHistory =
                historyRepository.save(history);

        System.out.println("Asset updated: " + asset.getStatus());
        System.out.println("History saved with ID: "
                + savedHistory.getId());

        return savedHistory;
    }
    
    

    // AUTOMATIC HISTORY RECORDING
    public void recordStatusChange(
            Asset asset,
            String previousStatus,
            String newStatus,
            String reason) {

        if (previousStatus == null || newStatus == null
                || previousStatus.equalsIgnoreCase(newStatus)) {
            return;
        }

        AssetStatusHistory history = new AssetStatusHistory();

        history.setAsset(asset);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setChangedAt(LocalDateTime.now());
        history.setReason(reason);

        historyRepository.save(history);
    }

    // READ ALL HISTORY
    public List<AssetStatusHistory> getAllHistory() {
        return historyRepository.findAll();
    }

    // READ HISTORY BY ID
    public AssetStatusHistory getHistoryById(Long id) {
        return historyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("History not found"));
    }

    // READ HISTORY BY ASSET
    public List<AssetStatusHistory> getHistoryByAsset(Long assetId) {
        return historyRepository
                .findByAssetIdOrderByChangedAtDesc(assetId);
    }

    @Transactional
    public AssetStatusHistory updateHistory(Long id, AssetStatusHistory updatedHistory) {

        AssetStatusHistory existingHistory = historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("History record not found"));

        Asset asset = existingHistory.getAsset();

        String newStatus = updatedHistory.getNewStatus();

        if (newStatus == null || newStatus.isBlank()) {
            throw new RuntimeException("New status is required");
        }

        // Update the asset's current status
        asset.setStatus(newStatus);
        assetRepository.save(asset);

        // Update the history record
        existingHistory.setNewStatus(newStatus);
        existingHistory.setReason(updatedHistory.getReason());

        return historyRepository.save(existingHistory);
    }
}
