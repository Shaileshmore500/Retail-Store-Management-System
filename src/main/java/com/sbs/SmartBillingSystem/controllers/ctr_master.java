package com.sbs.SmartBillingSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Product;
// import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;
import com.sbs.SmartBillingSystem.Entity.serializedObject.*;

@Controller
@RequestMapping("/master")
public class ctr_master {

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    ProductRepo productRepo;

    private final ObjectMapper objectMapper;

    public ctr_master(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/savecat")
    public String saveString(@RequestParam("name") String name, @RequestParam("code") String code, Model model) {
        Category cat = new Category();
        System.out.println("in cat add");
        cat.setCode(code);
        cat.setName(name);
        try {
            categoryRepo.save(cat);
            model.addAttribute("sucmessage", "Category Added...");
        } catch (Exception e) {

            System.out.println(e.getMessage());

            model.addAttribute("errormessage", code + " Already Present...");
            // model.addAttribute("data", cat);

        }

        return "forms/CategoryForm";
    }

    @PostMapping("/saveproduct")
    public ResponseEntity<String> saveProduct(@RequestBody List<DesObjProduct> desObjProduct) {
        System.out.println("in product 123");

        // productRepo.saveAll(lstProduct);
        List<Product> lst_Products = new ArrayList<Product>();

        for (DesObjProduct objproduct : desObjProduct) {
            Product p = new Product();
            Optional<Category> categoryoptional = categoryRepo.findById(objproduct.getCategory_fid());
            Category category = categoryoptional.orElseThrow(() -> new RuntimeException("Category not found"));

            Optional<Brand> brandoptional = brandRepo.findById(objproduct.getBrand_fid());
            Brand brand = brandoptional.orElseThrow(() -> new RuntimeException("Brand not found"));

            p.setName(objproduct.getName());
            p.setCode(objproduct.getCode());
            p.setCategory_fid(category);
            p.setPurchase_rate(objproduct.getPurchase_rate());
            p.setMRP(objproduct.getMrp());
            p.setBrand_fid(brand);
            p.setQuantity(objproduct.getQuantity());
            p.setTotal_amount(objproduct.getQuantity() * objproduct.getPurchase_rate());
            lst_Products.add(p);
        }
        productRepo.saveAll(lst_Products);
        System.out.println("saves");
        // Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);

        // // Now you have a Map representing the JSON object
        // // You can access values using keys
        // String name = (String) jsonMap.get("name");
        // var age = jsonMap.get("age");

        // List<Product> products = new ArrayList<Product>();
        // for (Product product : lstProduct) {

        // Category c = product.getCategory_fid();
        // System.out.println("----------------------------------" +
        // c.getCategory_pid());

        // Optional<Category> categoryoptional =
        // categoryRepo.findById(c.getCategory_pid());
        // Category category = categoryoptional.orElseThrow(() -> new
        // RuntimeException("Category not found"));
        // product.setCategory_fid(category);

        // Brand b = product.getBrand_fid();
        // Optional<Brand> brandoptional = brandRepo.findById(b.getBrand_pid());
        // Brand brand = brandoptional.orElseThrow(() -> new RuntimeException("Brand not
        // found"));
        // product.setBrand_fid(brand);

        // products.add(product);
        // }

        // Optional<Category> categoryOptional = categoryRepo.findById(1);

        // // Convert Optional<Category> to Category instance
        // // converting and setting category
        // // Category category = categoryOptional.orElseThrow(() -> new
        // // RuntimeException("Category not found"));
        // lstProduct.get(0).setCategory_fid(category);

        // System.out.println(lstProduct.get(0).getName());
        // System.out.println("in product save");
        // productRepo.save(lstProduct);

        return ResponseEntity.ok("ok");

    }

    @PostMapping("/savebrand")
    public String saveBrand(@RequestParam String name, @RequestParam String code, Model model) {
        Brand brand = new Brand();
        brand.setCode(code);
        brand.setName(name);
        try {
            brandRepo.save(brand);
            model.addAttribute("sucmessage", "Brand Added...");

        } catch (Exception e) {
            // model.addAttribute("errormessage", code + " Already Present..." +
            // e.getMessage());
            model.addAttribute("errormessage", e.getMessage());
            // model.addAttribute("data", brand);

        }

        return "forms/Brand";
    }

}
