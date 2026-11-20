
package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AssetStatusHistory;

public interface AssetStatusHistoryRepository
        extends JpaRepository<AssetStatusHistory, Long> {

    List<AssetStatusHistory>
    findByAssetIdOrderByChangedAtDesc(Long assetId);
}
