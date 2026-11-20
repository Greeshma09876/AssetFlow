
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.AssetReturn;

public interface AssetReturnRepository
        extends JpaRepository<AssetReturn, Long> {
	
	  boolean existsByAssignmentId(Long assignmentId);
}
