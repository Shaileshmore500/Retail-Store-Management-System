package com.sbs.SmartBillingSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Suppiler;
import com.sbs.SmartBillingSystem.Entity.User;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.SupplierRepo;
import com.sbs.SmartBillingSystem.Repository.UserRepo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            redirectAttributes.addFlashAttribute("status", "sucess");
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
            redirectAttributes.addFlashAttribute("status", "sucess");
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
            @RequestParam String GST_no,
            @RequestParam String email,
            RedirectAttributes redirectAttributes) {

        Suppiler supplier = new Suppiler();
        supplier.setName(name);
        supplier.setAddress(address);
        supplier.setGST_no(GST_no);
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
            redirectAttributes.addFlashAttribute("status", "sucess");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "error");
        }

        return "redirect:/view?form=supplier";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        try {

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

            if (!String.valueOf(user.getId()).isEmpty()) {
                redirectAttributes.addFlashAttribute("mode", "edit");
            } else {
                redirectAttributes.addFlashAttribute("mode", "add");
            }

            if (user.getRole() == "")
                user.setRole("ROLE_OTHER");
            user.setEnabled(true);

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User result = this.userrepository.save(user);

            redirectAttributes.addFlashAttribute("status", "sucess");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "error");
        }
        return "redirect:/view?form=customer";
    }

}
