package com.sbs.SmartBillingSystem.controllers;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.User;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.ChallanRepo;
import com.sbs.SmartBillingSystem.Repository.CustomerRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;
import com.sbs.SmartBillingSystem.Repository.SupplierRepo;
import com.sbs.SmartBillingSystem.Repository.UserRepo;
import java.util.*;

@RestController
public class GridRestController {

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
    BrandRepo brandRepo;
    @Autowired
    CategoryRepo categoryRepo;
 
    

    @GetMapping("/getFormData")
    public ResponseEntity<?> FormNameData(@RequestParam String FormName, @RequestParam String Pid) {

        if (FormName.equals("category"))
            return new ResponseEntity(categoryRepo.findById(Integer.parseInt(Pid)), HttpStatus.OK);
        if (FormName.toLowerCase() == "user") {

        } else if (FormName.toLowerCase().equals("brand")) {

            return new ResponseEntity(brandRepo.findById(Integer.parseInt(Pid)), HttpStatus.OK);

        } else if (FormName.toLowerCase().equals("product")) {
            return new ResponseEntity(productRepo.findById(Integer.parseInt(Pid)), HttpStatus.OK);
        } else if (FormName.toLowerCase().equals("customer")) {
            return new ResponseEntity(customerRepo.findById(Integer.parseInt(Pid)), HttpStatus.OK);
        } else if (FormName.toLowerCase().equals("supplier")) {
            return new ResponseEntity(supplierRepo.findById(Integer.parseInt(Pid)), HttpStatus.OK);
        }
        else if (FormName.toLowerCase().equals("user")) {
            return new ResponseEntity(userRepo.findById(Integer.parseInt(Pid)), HttpStatus.OK);
        }

        return null;
    }



    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam String id, @RequestParam String form) {
try{
        int pid = Integer.parseInt(id);
        if (form.equals("brand"))
            brandRepo.deleteById(pid);
        else if (form.equals("customer"))
            customerRepo.deleteById(pid);
        else if (form.equals("user"))
            userRepo.deleteById(pid);
        else if (form.equals("category"))
            categoryRepo.deleteById(pid);
        else if (form.equals("supplier"))
            supplierRepo.deleteById(pid);
            //redirectAttributes.addFlashAttribute("status", "sucess");
            return "Success";
        } catch (Exception e) {
            //redirectAttributes.addFlashAttribute("status", "error");
            return "Error";
        }
        
    }

    // @PostMapping("/savebrand")
    // public ResponseEntity<?> saveBrand(@RequestParam String brand_pid, @RequestParam String name,
    //         @RequestParam String code, Model model) {
       
    //     // Brand brand = new Brand();
    //     // brand.setCode(code);
    //     // brand.setName(name);
    //     // if(!brand_pid.isBlank())
    //     // brand.setBrand_pid(Integer.parseInt(brand_pid));
    //     try 
    //     {
    //         // brandRepo.save(brand);
    //         return new ResponseEntity( "hiii", HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    //     }

    // }
}
