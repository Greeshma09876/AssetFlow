
package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.AssetStatusHistory;
import com.example.demo.repository.AssetRepository;
import com.example.demo.service.AssetStatusHistoryService;

@Controller
@RequestMapping("/status-history")
public class AssetStatusHistoryController {

    private final AssetStatusHistoryService historyService;
    private final AssetRepository assetRepository;

    public AssetStatusHistoryController(
            AssetStatusHistoryService historyService,
            AssetRepository assetRepository) {

        this.historyService = historyService;
        this.assetRepository = assetRepository;
    }

    // DISPLAY ALL HISTORY
    @GetMapping
    public String getAllHistory(Model model) {

        model.addAttribute("historyList",
                historyService.getAllHistory());

        return "status-history";
    }

    // ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model) {

        model.addAttribute("history",
                new AssetStatusHistory());

        model.addAttribute("assets",
                assetRepository.findAll());

        return "add-status-history";
    }

    // SAVE HISTORY
    @PostMapping("/save")
    public String saveHistory(
            @ModelAttribute("history") AssetStatusHistory history,
            @RequestParam("assetId") Long assetId) {

        historyService.saveHistory(history, assetId);

        return "redirect:/status-history";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id, Model model) {

        model.addAttribute("history",
                historyService.getHistoryById(id));

        return "edit-status-history";
    }

    // UPDATE HISTORY
    @PostMapping("/update/{id}")
    public String updateHistory(
            @PathVariable Long id,
            @ModelAttribute AssetStatusHistory history) {

        historyService.updateHistory(id, history);

        return "redirect:/status-history";
    }
}
