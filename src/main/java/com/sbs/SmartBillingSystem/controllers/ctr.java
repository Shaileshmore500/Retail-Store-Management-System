package com.sbs.SmartBillingSystem.controllers;

import java.security.Principal;
import java.util.*;

import com.sbs.SmartBillingSystem.Entity.User;
import com.sbs.SmartBillingSystem.Repository.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Suppiler;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    ChallanRepo challanRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CustomerRepo customerRepo;


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
    public String home(Principal principal,HttpServletRequest request) {

        var a= principal.getName();
        request.getSession().removeAttribute("currentUser");
        request.getSession().setAttribute("currentUser", userRepo.getUserByUserName(a));
        return "home";
    }

    @GetMapping("/customer")
    public String customer() {
        return "forms/Customer";
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

    @GetMapping("/barcode")
    public String printbarcode(Model model) {

        model.addAttribute("challan", challanRepo.findAll());
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

        List<Category> categories= categoryRepo.findAll();
        model.addAttribute("data", categories);
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

    @GetMapping("/invoice")
    public String invoice() {
        return "forms/invoice";
    }

    @GetMapping("/signup")
    public String signup(Model model) {

        model.addAttribute("user", null);

        return "register";
    }

    @GetMapping("/po")
    public String po(Model model) {

        List<Suppiler> suppilers = supplierRepo.findAll();
        model.addAttribute("suppliers", suppilers);

        return "forms/po";
    }

    @GetMapping("/attendance")
    public String attendance() {
        return "forms/attendance";
    }

    @GetMapping("/searchProduct")
    public String searchProduct() {
        return "forms/searchProduct";
    }

    @GetMapping("/view")
    public String grid1(@RequestParam String form, Model model) {

        if(form.toLowerCase()=="user")
        {
        List<User> users = userRepo.findAll();
        model.addAttribute("data", users);
        }
        else if (form.toLowerCase().equals("category")) {
            List<Category> categories=categoryRepo.findAll();
            model.addAttribute("data", categories);
            
        }
        else if (form.toLowerCase().equals("brand")) {
            List<Brand> brands=brandRepo.findAll();
            model.addAttribute("data", brands);

        }
        else if (form.toLowerCase().equals("product")) {
            model.addAttribute("data", productRepo.findAll());
        }
        else if (form.toLowerCase().equals("customer")) {
            model.addAttribute("data", customerRepo.findAll());
        }
        else if (form.toLowerCase().equals("catrgory")) {
            model.addAttribute("data",categoryRepo.findAll());
        }
        else if (form.toLowerCase().equals("supplier")) {
            model.addAttribute("data",supplierRepo.findAll());
        }
        model.addAttribute("name", form);

        return "/Grid/gid1";
    }

    @GetMapping("/oldnav")
    public String old() {
        return "navbar copy";
    }

}
