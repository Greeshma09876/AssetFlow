
package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.AssetAssignment;

public interface AssetAssignmentRepository
        extends JpaRepository<AssetAssignment, Long> {

    boolean existsByAssetIdAndStatus(Long assetId, String status);
    List<AssetAssignment> findByStatus(String status);
}
