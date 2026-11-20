
package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.MaintenanceRecord;
import com.example.demo.service.MaintenanceService;
import com.example.demo.repository.AssetRepository;

@Controller
public class MaintenanceController {

    private final MaintenanceService maintenanceService;
    private final AssetRepository assetRepository;

    public MaintenanceController(
            MaintenanceService maintenanceService,
            AssetRepository assetRepository) {

        this.maintenanceService = maintenanceService;
        this.assetRepository = assetRepository;
    }

    // DISPLAY ALL RECORDS
    @GetMapping("/maintenance")
    public String getAllMaintenance(Model model) {

        model.addAttribute(
                "maintenanceRecords",
                maintenanceService.getAllMaintenance());

        return "maintenance";
    }

    // SHOW ADD FORM
    @GetMapping("/maintenance/add")
    public String showAddForm(Model model) {

        model.addAttribute(
                "maintenanceRecord",
                new MaintenanceRecord());

        model.addAttribute(
                "assets",
                assetRepository.findAll());

        return "add-maintenance";
    }

    // SAVE RECORD
    @PostMapping("/maintenance/save")
    public String saveMaintenance(
            @ModelAttribute MaintenanceRecord record,
            @RequestParam Long assetId) {

        maintenanceService.saveMaintenance(record, assetId);

        return "redirect:/maintenance";
    }

    // SHOW EDIT FORM
    @GetMapping("/maintenance/edit/{id}")
    public String showEditForm(
            @PathVariable Long id, Model model) {

        model.addAttribute(
                "maintenanceRecord",
                maintenanceService.getMaintenanceById(id));

        return "edit-maintenance";
    }

    // UPDATE RECORD
    @PostMapping("/maintenance/update/{id}")
    public String updateMaintenance(
            @PathVariable Long id,
            @ModelAttribute MaintenanceRecord record) {

        maintenanceService.updateMaintenance(id, record);

        return "redirect:/maintenance";
    }

    @PostMapping("/maintenance/delete/{id}")
    public String deleteMaintenance(@PathVariable Long id) {

        maintenanceService.deleteMaintenance(id);

        return "redirect:/maintenance";
    }
}
