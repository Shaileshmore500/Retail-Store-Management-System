package com.sbs.SmartBillingSystem.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;

@Controller
@RequestMapping("/master")
public class ctr_master {

    @Autowired
    CategoryRepo categoryRepo;

    @PostMapping("/savecat")
    public String saveString(@RequestParam("name") String name, @RequestParam("code") String code, Model model) {
        Category cat = new Category();

        cat.setCode(code);
        cat.setName(name);
        try {
            categoryRepo.save(cat);
            model.addAttribute("sucmessage", "Category Added...");
        } catch (Exception e) {
            model.addAttribute("errormessage", code + " Already Present...");
            model.addAttribute("data", cat);

        }

        return "forms/CategoryForm";
    }

}
