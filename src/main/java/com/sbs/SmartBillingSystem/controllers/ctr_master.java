package com.sbs.SmartBillingSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Challan;
import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Entity.Suppiler;
// import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.ChallanRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;
import com.sbs.SmartBillingSystem.Repository.SupplierRepo;
import com.sbs.SmartBillingSystem.Entity.serializedObject.*;

@Controller
// @RequestMapping("/master")
public class ctr_master {

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    ChallanRepo challanRepo;

    private final ObjectMapper objectMapper;

    public ctr_master(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/master/savecat")
    public String saveString(@RequestParam("name") String name, @RequestParam("code") String code, Model model) {
        Category cat = new Category();
        System.out.println("in cat add");
        cat.setCode(code);
        System.out.println("code" + code);
        System.out.println("name:" + name);
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

    @PostMapping("/master/saveproduct")
    public ResponseEntity<String> saveProduct(@RequestBody DesObjChallanProduct desObjChallanProduct)
            throws ParseException {

        DesObjChallan desObjChallan = desObjChallanProduct.getDesObjChallan();
        List<DesObjProduct> desObjProduct = desObjChallanProduct.getDesObjProduct();
        System.out.println("in product 1234");
        System.out.println(desObjChallan);
        // setting values in challan object
        Challan challan = new Challan();

        Optional<Suppiler> supplierOptional = supplierRepo.findById(Integer.parseInt(desObjChallan.getSupplier_fid()));
        Suppiler suppiler = supplierOptional.orElseThrow(() -> new RuntimeException("Supplier not found"));
        challan.setSupplier_fid(suppiler);
        challan.setAmount(desObjChallan.getAmount());
        challan.setChallan_date(new SimpleDateFormat("yyyy-MM-dd").parse(desObjChallan.getChallan_date()));
        challan.setChallan_no(desObjChallan.getChallan_no());
        challan.setPurchase_date(new Date());
        challan.setQuantity(desObjChallan.getQuantity());

        Challan savedChallan = challanRepo.save(challan);

        // setting values in product object
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
            p.setMrp(objproduct.getMrp());
            p.setBrand_fid(brand);
            p.setQuantity(objproduct.getQuantity());
            p.setTotal_amount(objproduct.getQuantity() * objproduct.getPurchase_rate());
            p.setChallan_fid(savedChallan);
            p.setStyle(suppiler.getCode().toString().substring(0, Math.min(suppiler.getCode().length(), 3)) + "-"
                    + brand.getCode().substring(0, Math.min(brand.getCode().length(), 3)) + "-"
                    + category.getCode().substring(0, Math.min(category.getCode().length(), 3)));
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

    @PostMapping("/master/savebrand")
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

    @PostMapping("/master/savesupplier")
    public String savesupplier(@RequestParam String name,
            @RequestParam String code,
            @RequestParam String mobile_no,
            @RequestParam String address,
            @RequestParam String GST_no,
            Model model) {

        Suppiler supplier = new Suppiler();
        supplier.setName(name);
        supplier.setAddress(address);
        supplier.setGST_no(GST_no);
        supplier.setCode(code);
        supplier.setMobile_no(mobile_no);

        try {
            supplierRepo.save(supplier);
            model.addAttribute("sucmessage", "Supplier Added...");

        } catch (Exception e) {
            // model.addAttribute("errormessage", code + " Already Present..." +
            // e.getMessage());
            model.addAttribute("errormessage", e.getMessage());
            // model.addAttribute("data", brand);

        }

        return "forms/Supplier";
    }

}
