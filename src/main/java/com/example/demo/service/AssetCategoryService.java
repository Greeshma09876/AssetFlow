
package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AssetCategory;
import com.example.demo.repository.AssetCategoryRepository;

@Service
public class AssetCategoryService {

    private final AssetCategoryRepository categoryRepository;

    public AssetCategoryService(AssetCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public AssetCategory saveCategory(AssetCategory category) {
        return categoryRepository.save(category);
    }

    public List<AssetCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public AssetCategory getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public AssetCategory updateCategory(Long id, AssetCategory updatedCategory) {

        AssetCategory existingCategory = categoryRepository.findById(id)
                .orElse(null);

        if (existingCategory != null) {
            existingCategory.setCategoryName(updatedCategory.getCategoryName());
            existingCategory.setDescription(updatedCategory.getDescription());

            return categoryRepository.save(existingCategory);
        }

        return null;
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
