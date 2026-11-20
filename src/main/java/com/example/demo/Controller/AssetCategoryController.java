package com.example.demo.Controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entity.AssetCategory;
import com.example.demo.service.AssetCategoryService;

@Controller
public class AssetCategoryController {

    private final AssetCategoryService categoryService;

    public AssetCategoryController(AssetCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Display all categories
    @GetMapping("/categories")
    public String getAllCategories(Model model) {

        model.addAttribute("categories", categoryService.getAllCategories());

        return "categories";
    }

    // Show add category form
    @GetMapping("/categories/add")
    public String showAddCategoryForm(Model model) {

        model.addAttribute("category", new AssetCategory());

        return "add-category";
    }

    // Save category
    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute AssetCategory category) {

        categoryService.saveCategory(category);

        return "redirect:/categories";
    }

    // Show edit category form
    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {

        model.addAttribute("category", categoryService.getCategoryById(id));

        return "edit-category";
    }

    // Update category
    @PostMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @ModelAttribute AssetCategory category) {

        categoryService.updateCategory(id, category);

        return "redirect:/categories";
    }

    // Delete category
    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return "redirect:/categories";
    }
}
