package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // tells hiberante that this java class represents a database table
@Table(name = "asset_categories") // if we create a AssetCategory class, based on this class hibernate need to
									// create a database table in mysql
public class AssetCategory {

	@Id // defines the primary key of the database table.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // automatically generates the primary key value
	private Long id;

	// @Column(name = "category_name")- providing the column name/field name for the
	// database
	// nullable=false - the field should contain values, cannot be null
	// unique =true = not allow column name twice
	@Column(nullable = false, unique = true)
	private String categoryName;

	private String description;

	public AssetCategory() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}