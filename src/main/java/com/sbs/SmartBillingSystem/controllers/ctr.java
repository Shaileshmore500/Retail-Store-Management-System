package com.sbs.SmartBillingSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;

import ch.qos.logback.core.model.Model;

@Controller
// if u want to use method base authorization then use this anotation
// @EnableMethodSecurity
public class ctr {

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

    @GetMapping("/category")
    public String category(org.springframework.ui.Model model) {
        // Category cat = new Category();

        // cat.setCode("code");
        // cat.setName("name");

        // model.addAttribute("data", cat);

        return "CategoryForm";
    }

}
