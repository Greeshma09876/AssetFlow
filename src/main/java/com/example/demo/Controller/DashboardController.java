package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.DashboardService;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        model.addAttribute("totalAssets",
                dashboardService.getTotalAssets());

        model.addAttribute("availableAssets",
                dashboardService.getAvailableAssets());

        model.addAttribute("assignedAssets",
                dashboardService.getAssignedAssets());

        model.addAttribute("underRepairAssets",
                dashboardService.getUnderRepairAssets());

        model.addAttribute("retiredAssets",
                dashboardService.getRetiredAssets());

        model.addAttribute("lostAssets",
                dashboardService.getLostAssets());

        return "dashboard";
    }
}