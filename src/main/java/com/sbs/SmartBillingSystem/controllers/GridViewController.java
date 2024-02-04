package com.sbs.SmartBillingSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Customer;
import com.sbs.SmartBillingSystem.Entity.Suppiler;
import com.sbs.SmartBillingSystem.Entity.User;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.CustomerRepo;
import com.sbs.SmartBillingSystem.Repository.SupplierRepo;
import com.sbs.SmartBillingSystem.Repository.UserRepo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GridViewController {
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    UserRepo userrepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomerRepo customerRepo;

    @PostMapping("/savebrand")
    public String saveBrand(@RequestParam String brand_pid, @RequestParam String name,
            @RequestParam String code, Model model, RedirectAttributes redirectAttributes) {

        Brand brand = new Brand();
        brand.setCode(code);
        brand.setName(name);
        if (!brand_pid.isBlank()) {
            brand.setBrand_pid(Integer.parseInt(brand_pid));
            redirectAttributes.addFlashAttribute("mode", "edit");
        } else {
            redirectAttributes.addFlashAttribute("mode", "add");
        }
        try {
            brandRepo.save(brand);
            redirectAttributes.addFlashAttribute("status", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "error");
        }

        return "redirect:/view?form=brand";
    }

    @PostMapping("/savecat")
    public String saveString(@RequestParam("category_pid") String category_pid, @RequestParam("name") String name,
            @RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        Category cat = new Category();

        cat.setCode(code);
        cat.setName(name);
        if (!category_pid.isEmpty()) {
            cat.setCategory_pid(Integer.parseInt(category_pid));
            redirectAttributes.addFlashAttribute("mode", "edit");
        } else {
            redirectAttributes.addFlashAttribute("mode", "add");
        }
        try {
            categoryRepo.save(cat);
            redirectAttributes.addFlashAttribute("status", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "error");
        }

        return "redirect:/view?form=category";

    }

    @PostMapping("/savesupplier")
    public String savesupplier(
            @RequestParam String supplier_pid,
            @RequestParam String name,
            @RequestParam String code,
            @RequestParam String mobile_no,
            @RequestParam String address,
            @RequestParam String gst_no,
            @RequestParam String email,
            RedirectAttributes redirectAttributes) {

        Suppiler supplier = new Suppiler();
        supplier.setName(name);

        supplier.setAddress(address);
        supplier.setGST_no(gst_no);
        supplier.setCode(code);
        supplier.setMobile_no(mobile_no);
        supplier.setEmail(email);

        if (!supplier_pid.isBlank()) {
            supplier.setSupplier_pid(Integer.parseInt(supplier_pid));
            redirectAttributes.addFlashAttribute("mode", "edit");
        } else {
            redirectAttributes.addFlashAttribute("mode", "add");
        }

        try {
            supplierRepo.save(supplier);
            redirectAttributes.addFlashAttribute("status", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "error");
        }

        return "redirect:/view?form=supplier";
    }

    @PostMapping("/registerUser")
    // public String registerUser(@ModelAttribute("user") User user,
    // @RequestParam("file") MultipartFile file, RedirectAttributes
    // redirectAttributes)
    public String registerUser(@RequestParam String id,
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String mobile_no,
            @RequestParam String role,
            @RequestParam String address,
            @RequestParam String password, @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        try {

            User user = new User();
            user.setEmail(email);
            user.setAddress(address);
            user.setEnabled(true);
            user.setMobile_no(mobile_no);
            user.setName(name);
            user.setPassword(password);
            user.setRole(role);

            if (file != null) {

                String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

                Path resourcesPath = Paths
                        .get("src", "main", "resources", "static", "images", timestamp + file.getOriginalFilename())
                        .toAbsolutePath();

                byte[] img_byte = file.getBytes();
                Files.write(resourcesPath, img_byte);

                if (resourcesPath == null)
                    user.setImageUrl("default.png");
                else
                    user.setImageUrl("/images/" + timestamp + file.getOriginalFilename());
                ;

            }

            if (!id.isEmpty()) {
                user.setId(Integer.parseInt(id));
                redirectAttributes.addFlashAttribute("mode", "edit");
            } else {
                redirectAttributes.addFlashAttribute("mode", "add");
            }

            if (user.getRole() == "")
                user.setRole("ROLE_OTHER");
            user.setEnabled(true);

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            this.userrepository.save(user);

            redirectAttributes.addFlashAttribute("status", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "error");
        }
        return "redirect:/view?form=user";
    }

    @PostMapping("/savecustomer")
    public String savecustomer(@RequestParam String customer_pid, @RequestParam String name, @RequestParam String email,
            @RequestParam String mobile_no, @RequestParam String dob, @RequestParam String address,
            RedirectAttributes redirectAttributes) {

        try {
            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
            customer.setEmail(email);
            customer.setMobile_no(mobile_no);
            customer.setName(name);

            if (!customer_pid.isEmpty()) {
                customer.setCustomer_pid(Integer.parseInt(customer_pid));
                redirectAttributes.addFlashAttribute("mode", "edit");
            } else {
                redirectAttributes.addFlashAttribute("mode", "add");
            }

            customerRepo.save(customer);

            redirectAttributes.addFlashAttribute("status", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "error");
        }
        return "redirect:/view?form=customer";

    }

//     @GetMapping("/deleteItem")
//     public String deleteItem(@RequestParam String id, @RequestParam String form,RedirectAttributes redirectAttributes) {
// try{
//         int pid = Integer.parseInt(id);
//         if (form.equals("brand"))
//             brandRepo.deleteById(pid);
//         else if (form.equals("customer"))
//             customerRepo.deleteById(pid);
//         else if (form.equals("user"))
//             userrepository.deleteById(pid);
//         else if (form.equals("category"))
//             categoryRepo.deleteById(pid);
//         else if (form.equals("supplier"))
//             supplierRepo.deleteById(pid);
//             redirectAttributes.addFlashAttribute("status", "success");
//         } catch (Exception e) {
//             redirectAttributes.addFlashAttribute("status", "error");
//         }
//         redirectAttributes.addFlashAttribute("mode", "edit");
//             return "redirect:/view?form=supplier;//"+form;
//     }

}
