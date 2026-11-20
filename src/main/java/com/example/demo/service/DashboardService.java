package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.AssetRepository;

@Service
public class DashboardService {

    private final AssetRepository assetRepository;

    public DashboardService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public long getTotalAssets() {
        return assetRepository.count();
    }

    public long getAvailableAssets() {
        return assetRepository.countByStatus("Available");
    }

    public long getAssignedAssets() {
        return assetRepository.countByStatus("Assigned");
    }

    public long getUnderRepairAssets() {
        return assetRepository.countByStatus("Under Repair");
    }

    public long getRetiredAssets() {
        return assetRepository.countByStatus("Retired");
    }

    public long getLostAssets() {
        return assetRepository.countByStatus("Lost");
    }
}