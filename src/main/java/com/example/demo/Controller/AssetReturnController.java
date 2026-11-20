
package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.AssetReturn;
import com.example.demo.service.AssetReturnService;

@Controller
public class AssetReturnController {

    private final AssetReturnService returnService;

    public AssetReturnController(
            AssetReturnService returnService) {
        this.returnService = returnService;
    }

    // DISPLAY ALL RETURNS
    @GetMapping("/returns")
    public String getAllReturns(Model model) {

        model.addAttribute(
                "returns", returnService.getAllReturns());

        return "returns";
    }

    // SHOW ADD FORM
    @GetMapping("/returns/add")
    public String showAddReturnForm(Model model) {

        model.addAttribute("assetReturn", new AssetReturn());

        model.addAttribute(
                "assignments", returnService.getActiveAssignments());

        return "add-return";
    }

    // SAVE RETURN
    @PostMapping("/returns/save")
    public String saveReturn(
            @ModelAttribute AssetReturn assetReturn,
            @RequestParam Long assignmentId) {

        returnService.saveReturn(assetReturn, assignmentId);

        return "redirect:/returns";
    }

    // SHOW EDIT FORM
    @GetMapping("/returns/edit/{id}")
    public String showEditReturnForm(
            @PathVariable Long id, Model model) {

        model.addAttribute(
                "assetReturn", returnService.getReturnById(id));

        return "edit-return";
    }

    // UPDATE RETURN
    @PostMapping("/returns/update/{id}")
    public String updateReturn(
            @PathVariable Long id,
            @ModelAttribute AssetReturn assetReturn) {

        returnService.updateReturn(id, assetReturn);

        return "redirect:/returns";
    }

    // DELETE RETURN
    @PostMapping("/returns/delete/{id}")
    public String deleteReturn(@PathVariable Long id) {

        returnService.deleteReturn(id);

        return "redirect:/returns";
    }
}
