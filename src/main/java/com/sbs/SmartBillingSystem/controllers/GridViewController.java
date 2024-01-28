package com.sbs.SmartBillingSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import java.util.*;

@Controller
public class GridViewController {
    @Autowired
    BrandRepo brandRepo;

    @PostMapping("/savebrand")
    public String saveBrand(@RequestParam String brand_pid, @RequestParam String name,
            @RequestParam String code, Model model,RedirectAttributes redirectAttributes) {

        Brand brand = new Brand();
        brand.setCode(code);
        brand.setName(name);
        if (!brand_pid.isBlank())
            brand.setBrand_pid(Integer.parseInt(brand_pid));
        try {
            brandRepo.save(brand);
            redirectAttributes.addFlashAttribute("status","sucess");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status","error");
        }
        
       
        return "redirect:/view?form=brand";
    }

}
