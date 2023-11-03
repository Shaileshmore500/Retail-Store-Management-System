package com.sbs.SmartBillingSystem.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Suppiler;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.SupplierRepo;

import ch.qos.logback.core.model.Model;

@Controller

// if u want to use method base authorization then use this anotation
// @EnableMethodSecurity
public class ctr {

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    SupplierRepo supplierRepo;

    @GetMapping("/home")
    public String homepage() {
        return "home";
    }

    // if u want to use method base authorization then use this anotation
    // @PreAuthorize("hasRoll('ADMIN')")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/supplier")
    public String supplier(org.springframework.ui.Model model) {
        // Category cat = new Category();

        // cat.setCode("code");
        // cat.setName("name");

        // model.addAttribute("data", cat);
        // model.addAttribute("errormessage", null);
        // model.addAttribute("sucmessage", null);

        return "forms/Supplier";
    }

    @GetMapping("/printbarcode")
    public String printbarcode() {
        return "forms/PrintBarcode";
    }

    @GetMapping("/category")
    public String category(org.springframework.ui.Model model) {
        // Category cat = new Category();

        // cat.setCode("code");
        // cat.setName("name");

        // model.addAttribute("data", cat);
        // model.addAttribute("errormessage", null);
        // model.addAttribute("sucmessage", null);

        return "forms/CategoryForm";
    }

    // @GetMapping("/nav")
    // public String nav() {
    // // Category cat = new Category();

    // // cat.setCode("code");
    // // cat.setName("name");

    // // model.addAttribute("data", cat);

    // return "navbar";
    // }

    @GetMapping("/product")
    public String product(org.springframework.ui.Model model) {

        List<Category> categories = categoryRepo.findAll();
        List<Brand> brands = brandRepo.findAll();
        List<Suppiler> suppilers = supplierRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("suppliers", suppilers);

        return "forms/product";
    }

    @GetMapping("/brand")
    public String brand() {
        return "forms/brand";
    }

}
