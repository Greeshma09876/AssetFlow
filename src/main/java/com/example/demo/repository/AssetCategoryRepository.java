package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.AssetCategory;

@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory,Long> {//repository tells spring JPA which entity and primary key to manage  

	//here, Spring Data JPA automatically provides the implementation of the repository interface at runtime.
	//therefore, we do not need to write implementation class manually.
	//AssetCategory - specifies the entity that this repository will manage.which is mapped to the "asset_categories" database table.
	//Long - represents the datatype of the primary key of that entity class
	
}
