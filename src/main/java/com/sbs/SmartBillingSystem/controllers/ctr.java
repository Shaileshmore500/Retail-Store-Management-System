package com.sbs.SmartBillingSystem.controllers;

import java.security.Principal;
import java.util.*;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.data.jpa.repository.Query;

import com.sbs.SmartBillingSystem.Entity.*;
import com.sbs.SmartBillingSystem.Repository.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    BillRepo billRepo;
    @Autowired
    BillDetailRepo billDetailRepo;
    @Autowired
    EntityManager entityManager;

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
    public String home(Principal principal, HttpServletRequest request) {

        var a = principal.getName();
        request.getSession().removeAttribute("currentUser");
        request.getSession().setAttribute("currentUser", userRepo.getUserByUserName(a));
try {
   var abc=billRepo.findMonthlySalesByYear(2024);
// Process the results as needed

}catch (Exception e)
{
    System.out.println(e.getMessage());
}













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

        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("data", categories);
        return "forms/CategoryForm";
    }

    @GetMapping("/product")
    public String product(
            @RequestParam String pid, Model model) {

        Challan challan = new Challan();
        List<Category> categories = new ArrayList<>();
        List<Brand> brands = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        List<Suppiler> suppilers = new ArrayList<>();
        try {
            categories = categoryRepo.findAll();
            brands = brandRepo.findAll();
            suppilers = supplierRepo.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("brands", brands);
            model.addAttribute("suppliers", suppilers);

            if (!pid.isEmpty()) {
                // Challan challan= challanRepo.findById(Integer.parseInt(pid)).orElseThrow();
                challan = challanRepo.findById(Integer.parseInt(pid))
                        .orElseThrow(() -> new NoSuchElementException("Challan not found with ID: " + pid));

                if (challan.getSupplier_fid() != null)
                    challan.set_supplier_fid(challan.getSupplier_fid().getSupplier_pid());

                if (challan != null) {
                    products = productRepo.findByChallan_fid(challan.getPartyChallan_pid());

                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (products.size() == 0) {
            Product product = new Product();
            product.setName("product 1");
            products.add(product);
        }
        model.addAttribute("challan", challan);
        model.addAttribute("products", products);
        // redirectAttributes.addFlashAttribute("challan", challan);
        // redirectAttributes.addFlashAttribute("products", products);
        // redirectAttributes.addFlashAttribute("categories", categories);
        // redirectAttributes.addFlashAttribute("brands", brands);
        // redirectAttributes.addFlashAttribute("suppliers", suppilers);
        // return "redirect:/addproduct";
        return "forms/product";
    }

    @GetMapping("/brand")
    public String brand() {
        return "forms/brand";
    }

    @GetMapping("/invoice")
    public String invoice(@RequestParam String id, Model model) {
        model.addAttribute("customer", customerRepo.findAll());

        Bill bill = null;
        List<BillDetails> billDetails = null;
        try {

            if (!id.isEmpty()) {
                bill = billRepo.findById(Integer.parseInt(id)).get();
                billDetails = billDetailRepo.getBillDetailsByBill_fid(bill);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        model.addAttribute("bill", bill);
        model.addAttribute("billdetails", billDetails);

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
    public String searchProduct(Model model) {

        List<Category> categories = new ArrayList<>();
        List<Brand> brands = new ArrayList<>();

        try {
            categories = categoryRepo.findAll();
            brands = brandRepo.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("brands", brands);

        } catch (Exception exception) {

        }

        return "forms/searchProduct";
    }

    @GetMapping("/view")
    public String grid1(@RequestParam String form, Model model) {

        if (form.toLowerCase() == "user") {
            List<User> users = userRepo.findAll();
            model.addAttribute("data", users);
        } else if (form.toLowerCase().equals("category")) {
            List<Category> categories = categoryRepo.findAll();
            model.addAttribute("data", categories);

        } else if (form.toLowerCase().equals("brand")) {
            List<Brand> brands = brandRepo.findAll();
            model.addAttribute("data", brands);

        } else if (form.toLowerCase().equals("product")) {
            model.addAttribute("data", productRepo.findAll());
        } else if (form.toLowerCase().equals("customer")) {
            model.addAttribute("data", customerRepo.findAll());
        } else if (form.toLowerCase().equals("catrgory")) {
            model.addAttribute("data", categoryRepo.findAll());
        } else if (form.toLowerCase().equals("supplier")) {
            model.addAttribute("data", supplierRepo.findAll());
        } else if (form.toLowerCase().equals("user")) {
            model.addAttribute("data", userRepo.findAll());
        } else if (form.toLowerCase().equals("challan")) {
            model.addAttribute("data", challanRepo.findAll());
            return "/Grid/gridChallan";
        } else if (form.equals("invoice")) {
            var a = billRepo.findAll();
            model.addAttribute("data", billRepo.findAll());
            model.addAttribute("name", form);
            return "/Grid/gridInvoice";

        }
        model.addAttribute("name", form);

        return "/Grid/gid1";
    }

    @GetMapping("/oldnav")
    public String old() {
        return "navbar copy";
    }

    @GetMapping("/ForgotPAssword")
    public String ForgotPassword() {

        return "forms/Forgot";
    }

    @GetMapping("/changepassword")
    public String changepassword() {
        return "forms/changepassword";
    }

}
