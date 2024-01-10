package com.sbs.SmartBillingSystem.controllers;

import jakarta.servlet.ServletContext;
import org.apache.catalina.core.ApplicationContext;
import org.apache.tomcat.util.descriptor.web.MultipartDef;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.SmartBillingSystem.Entity.Bill;
import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Challan;
import com.sbs.SmartBillingSystem.Entity.Customer;
import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Entity.PurchaseOrder;
import com.sbs.SmartBillingSystem.Entity.Suppiler;

import com.sbs.SmartBillingSystem.Repository.BillRepo;

import com.sbs.SmartBillingSystem.Entity.User;
// import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.ChallanRepo;
import com.sbs.SmartBillingSystem.Repository.CustomerRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;
import com.sbs.SmartBillingSystem.Repository.PurchaseOrderRepo;
import com.sbs.SmartBillingSystem.Repository.SupplierRepo;
import com.sbs.SmartBillingSystem.Repository.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.sbs.SmartBillingSystem.Entity.serializedObject.*;
import com.sbs.SmartBillingSystem.Helper.EmailHelper;
import com.sbs.SmartBillingSystem.Helper.InvoiveHelper;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    InvoiveHelper invoiveHelper;
    @Autowired
    BillRepo billRepo;
    @Autowired
    PurchaseOrderRepo purchaseOrderRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userrepository;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired 
    UserRepo userRepo;

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
                    + category.getCode().substring(0, Math.min(category.getCode().length(), 3))
                    + "#" + p.getSize());
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
                               @RequestParam String email,
            Model model) {

        Suppiler supplier = new Suppiler();
        supplier.setName(name);
        supplier.setAddress(address);
        supplier.setGST_no(GST_no);
        supplier.setCode(code);
        supplier.setMobile_no(mobile_no);
        supplier.setEmail(email);

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

    @PostMapping("/generateinvoice")
    //public ResponseEntity<?> generateInvoice(@RequestBody List<Product> p) {
        public ResponseEntity<?> generateInvoice(@RequestBody DesObjBillProduct billProduct,Principal principal) {
        try{

        List<Product> productList = billProduct.getProduct();

        List<String> errorList = invoiveHelper.validateinvoice(productList);

        if (errorList.size() > 0) {
            return new ResponseEntity<>(errorList, HttpStatus.NOT_FOUND);
        }
        Bill bill = billProduct.getBill();
        try {
            Customer customer=customerRepo.findById(Integer.parseInt(bill.get_customer_fid())).orElseThrow();
        bill.setCustomer_fid(customer);
        } catch (Exception e) {
            
        }
        //User user=this.userRepo.getUserByUserName(principal.getName());
       
       
        Bill bill2 = billRepo.save(bill);

        boolean updateStatus = invoiveHelper.updateProduct(productList, bill2);
        if(updateStatus)
        return new ResponseEntity<>("Invoice Generated successfully",HttpStatus.OK);

    }catch (Exception e) {
       return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        
    }
    return new ResponseEntity<>("Something Went Wrong....",HttpStatus.CONFLICT);
        
    }

    @PostMapping("/master/registerUser")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam("file") MultipartFile file

    ) {

        try {

            if (file != null) {

                // Path path= Paths.get(staticPath,file.getOriginalFilename());
                // byte[] img_byte=file.getBytes();
                // Files.write(path,img_byte);
                // user.setImageUrl(path.toString());
                // File saveFile = new ClassPathResource("static/images").getFile();
                // Path path = Paths.get(saveFile.getAbsolutePath() + File.separator +
                // file.getOriginalFilename());
                // Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

                Path resourcesPath = Paths
                        .get("src", "main", "resources", "static", "images", timestamp + file.getOriginalFilename())
                        .toAbsolutePath();

                byte[] img_byte = file.getBytes();
                Files.write(resourcesPath, img_byte);

                if (resourcesPath == null)
                    user.setImageUrl("default.png");
                else
                    user.setImageUrl(timestamp + file.getOriginalFilename());
                ;

            }

            if (user.getRole() == "")
                user.setRole("ROLE_OTHER");
            user.setEnabled(true);

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User result = this.userrepository.save(user);
            return "register";

        } catch (Exception e) {

            return "register";
        }

    }

    @PostMapping("/savePO")
    public ResponseEntity<?> savePO(@RequestParam("file") MultipartFile file, @RequestParam String ponumber,
            @RequestParam String supplier_fid,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date poDate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date podueDate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date poreceivedDate,
        
            @RequestParam String amt,
            @RequestParam String qty, @RequestParam String note) {
        try {
            byte[] attchment=file.getInputStream().readAllBytes();
        PurchaseOrder order = new PurchaseOrder();
        Suppiler suppiler = supplierRepo.findById(Integer.parseInt(supplier_fid)).orElseThrow();
        order.setSuppiler_fid(suppiler);
        order.setAmount(Double.parseDouble(amt));
        order.setQuantity(Double.parseDouble(qty));
        order.setNotes(note);
        order.setCreatedDate(poDate);
        order.setDueDate(podueDate);
        order.setReceivedDate(poreceivedDate);
        order.setAttchmentByte(attchment);
        order.setFileName(file.getOriginalFilename());
        purchaseOrderRepo.save(order);
            var receiver=suppiler.getEmail();
            var subject="Purchase Order - [Your Company Name]";
            var filename=file.getOriginalFilename();
            //StringBuilder body_Builder=new StringBuilder();

            // body_Builder.append("Dear "+suppiler.getName()+",\r\n" + //
            //         "\r\n" + //
            //         "I trust this email finds you well. We are excited to place a purchase order with your company for the following items:\r\n" + //
                  
            //         "Total Estimated Purchase Order Cost: [Sum of all estimated costs]\r\n" + //
            //         "\r\n" + //
            //         "Please find attached the detailed Purchase Order document containing specifications, terms, and conditions. Kindly review the document thoroughly, and confirm your acceptance at your earliest convenience.\r\n" + //
            //         "\r\n" + //
            //         "Attachment: [Attach the Purchase Order document]\r\n" + //
            //         "\r\n" + //
            //        "Delevery Note : "+order.getNotes()+
            //         "\r\n" + //
                    
            //         "Delivery Date : "+order.getDueDate()+"\r\n" + //
            //         "Delivery Location: [Provide the delivery address]\r\n" + //
            //         "Should there be any discrepancies or if you require further details, please do not hesitate to contact us promptly.\r\n" + //
            //         "\r\n" + //
            //         "Your swift attention to this matter is highly appreciated. We anticipate a successful collaboration and thank you for your cooperation.\r\n" + //
            //         "\r\n" + //
            //         "Best regards,\r\n" + //
            //         "\r\n" + //
            //         "[Your Full Name]\r\n" + //
            //         "[Your Position]\r\n" + //
            //         "[Your Company Name]\r\n" + //
            //         "[Your Contact Information]");
                    var isSendmail= EmailHelper.sendMail(subject, "body_Builder".toString(), attchment, receiver,filename );
                    if(isSendmail)
                    return new ResponseEntity<>("Purchase Order Generated Succesfull...",HttpStatus.OK);
            



        }catch (Exception e)
        {
            return new ResponseEntity<>("Something Went Wrong...",HttpStatus.BAD_REQUEST);

        }





         return new ResponseEntity<>("Something Went Wrong...",HttpStatus.BAD_REQUEST);
    }

}
