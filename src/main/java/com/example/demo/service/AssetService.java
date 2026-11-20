
package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Asset;
import com.example.demo.entity.AssetCategory;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.AssetCategoryRepository;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetCategoryRepository categoryRepository;

    public AssetService(
            AssetRepository assetRepository,
            AssetCategoryRepository categoryRepository) {

        this.assetRepository = assetRepository;
        this.categoryRepository = categoryRepository;
    }

    // Save Asset
    public Asset saveAsset(Asset asset, Long categoryId) {

        AssetCategory category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        asset.setCategory(category);

        return assetRepository.save(asset);
    }

    // Get All Assets
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    // Get Asset By ID
    public Asset getAssetById(Long id) {
        return assetRepository.findById(id).orElse(null);
    }

    // Update Asset
    public Asset updateAsset(Long id, Asset updatedAsset, Long categoryId) {

        Asset existingAsset = assetRepository.findById(id).orElse(null);

        if (existingAsset != null) {

            AssetCategory category = categoryRepository
                    .findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            existingAsset.setAssetTag(updatedAsset.getAssetTag());
            existingAsset.setAssetName(updatedAsset.getAssetName());
            existingAsset.setBrand(updatedAsset.getBrand());
            existingAsset.setModel(updatedAsset.getModel());
            existingAsset.setSerialNumber(updatedAsset.getSerialNumber());
            existingAsset.setPurchaseDate(updatedAsset.getPurchaseDate());
            existingAsset.setWarrantyExpiryDate(updatedAsset.getWarrantyExpiryDate());
            existingAsset.setStatus(updatedAsset.getStatus());
            existingAsset.setLocation(updatedAsset.getLocation());
            existingAsset.setCategory(category);

            return assetRepository.save(existingAsset);
        }

        return null;
    }

    // Delete Asset
    public void deleteAsset(Long id) {
        assetRepository.deleteById(id);
    }
}
