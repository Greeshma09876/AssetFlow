
package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.MaintenanceRecord;

public interface MaintenanceRepository
        extends JpaRepository<MaintenanceRecord, Long> {

    List<MaintenanceRecord> findByAssetId(Long assetId);

    boolean existsByAssetIdAndMaintenanceStatusIn(
            Long assetId, List<String> statuses);
}
