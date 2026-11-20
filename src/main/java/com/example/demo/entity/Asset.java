package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class Asset {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String assetTag;

	    @Column(nullable = false)
	    private String assetName;

	    private String brand;
	    private String model;
	    private String serialNumber;
	    private LocalDate purchaseDate;
	    private LocalDate warrantyExpiryDate;
	    private String status;
	    private String location;

	    @ManyToOne
	    @JoinColumn(name = "category_id")
	    private AssetCategory category;

	    public Asset() {
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getAssetTag() {
	        return assetTag;
	    }

	    public void setAssetTag(String assetTag) {
	        this.assetTag = assetTag;
	    }

	    public String getAssetName() {
	        return assetName;
	    }

	    public void setAssetName(String assetName) {
	        this.assetName = assetName;
	    }

	    public String getBrand() {
	        return brand;
	    }

	    public void setBrand(String brand) {
	        this.brand = brand;
	    }

	    public String getModel() {
	        return model;
	    }

	    public void setModel(String model) {
	        this.model = model;
	    }

	    public String getSerialNumber() {
	        return serialNumber;
	    }

	    public void setSerialNumber(String serialNumber) {
	        this.serialNumber = serialNumber;
	    }

	    public LocalDate getPurchaseDate() {
	        return purchaseDate;
	    }

	    public void setPurchaseDate(LocalDate purchaseDate) {
	        this.purchaseDate = purchaseDate;
	    }

	    public LocalDate getWarrantyExpiryDate() {
	        return warrantyExpiryDate;
	    }

	    public void setWarrantyExpiryDate(LocalDate warrantyExpiryDate) {
	        this.warrantyExpiryDate = warrantyExpiryDate;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getLocation() {
	        return location;
	    }

	    public void setLocation(String location) {
	        this.location = location;
	    }

	    public AssetCategory getCategory() {
	        return category;
	    }

	    public void setCategory(AssetCategory category) {
	        this.category = category;
	    }

}
