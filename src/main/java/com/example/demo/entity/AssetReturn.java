
package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "asset_returns")
public class AssetReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "assignment_id", nullable = false, unique = true)
    private AssetAssignment assignment;

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column(name = "asset_condition")
    private String assetCondition;

    private String accessoriesReceived;

    private String remarks;

    public AssetReturn() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(AssetAssignment assignment) {
        this.assignment = assignment;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getAssetCondition() {
        return assetCondition;
    }

    public void setAssetCondition(String assetCondition) {
        this.assetCondition = assetCondition;
    }

    public String getAccessoriesReceived() {
        return accessoriesReceived;
    }

    public void setAccessoriesReceived(String accessoriesReceived) {
        this.accessoriesReceived = accessoriesReceived;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
