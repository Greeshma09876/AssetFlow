
package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Asset;
import com.example.demo.service.AssetService;
import com.example.demo.service.AssetCategoryService;

@Controller
public class AssetController {

    private final AssetService assetService;
    private final AssetCategoryService categoryService;

    public AssetController(
            AssetService assetService,
            AssetCategoryService categoryService) {

        this.assetService = assetService;
        this.categoryService = categoryService;
    }

    // Display All Assets
    @GetMapping("/assets")
    public String getAllAssets(Model model) {

        model.addAttribute("assets", assetService.getAllAssets());

        return "assets";
    }

    // Display Add Asset Form
    @GetMapping("/assets/add")
    public String showAddAssetForm(Model model) {

        model.addAttribute("asset", new Asset());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "add-asset";
    }

    // Save Asset
    @PostMapping("/assets/save")
    public String saveAsset(
            @ModelAttribute Asset asset,
            @RequestParam Long categoryId) {

        assetService.saveAsset(asset, categoryId);

        return "redirect:/assets";
    }

    // Display Edit Asset Form
    @GetMapping("/assets/edit/{id}")
    public String editAsset(@PathVariable Long id, Model model) {

        model.addAttribute("asset", assetService.getAssetById(id));
        model.addAttribute("categories", categoryService.getAllCategories());

        return "edit-asset";
    }

    // Update Asset
    @PostMapping("/assets/update/{id}")
    public String updateAsset(
            @PathVariable Long id,
            @ModelAttribute Asset asset,
            @RequestParam Long categoryId) {

        assetService.updateAsset(id, asset, categoryId);

        return "redirect:/assets";
    }

    // Delete Asset
    @GetMapping("/assets/delete/{id}")
    public String deleteAsset(@PathVariable Long id) {

        assetService.deleteAsset(id);

        return "redirect:/assets";
    }
}
