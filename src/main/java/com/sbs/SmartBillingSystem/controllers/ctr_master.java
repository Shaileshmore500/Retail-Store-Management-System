package com.sbs.SmartBillingSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.SmartBillingSystem.Entity.Bill;
import com.sbs.SmartBillingSystem.Entity.BillDetails;
import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Challan;
import com.sbs.SmartBillingSystem.Entity.Customer;
import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Entity.PurchaseOrder;
import com.sbs.SmartBillingSystem.Entity.Suppiler;
import com.sbs.SmartBillingSystem.Repository.BillDetailRepo;
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
import com.sbs.SmartBillingSystem.Services.EmailService;
import com.sbs.SmartBillingSystem.Services.InvoiceService;
import com.sbs.SmartBillingSystem.Entity.serializedObject.*;
//<<<<<<< HEAD

import org.springframework.web.multipart.MultipartFile;
//>>>>>>> bd73ce8323737b0d97e12ef35a3914d69be88555
//=======
import com.sbs.SmartBillingSystem.Helper.EmailHelper;

import static org.springframework.http.ResponseEntity.notFound;

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
    // @Autowired
    // InvoiveHelper invoiveHelper;
    @Autowired
    InvoiceService invoiceService;
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
    @Autowired
    BillDetailRepo billDetailRepo;
    private EmailService emailService;

    public ctr_master(EmailService emailService) {
        this.emailService = emailService;
    }

    // private final ObjectMapper objectMapper;
    // @Autowired
    // public ctr_master( ObjectMapper objectMapper) {
    // this.objectMapper = objectMapper;
    // }

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
        try {

            DesObjChallan desObjChallan = desObjChallanProduct.getDesObjChallan();
            List<DesObjProduct> desObjProduct = desObjChallanProduct.getDesObjProduct();
            System.out.println("in product 1234");
            System.out.println(desObjChallan);
            // setting values in challan object
            Challan challan = new Challan();

            Optional<Suppiler> supplierOptional = supplierRepo
                    .findById(Integer.parseInt(desObjChallan.getSupplier_fid()));
            Suppiler suppiler = supplierOptional.orElseThrow(() -> new RuntimeException("Supplier not found"));
            challan.setSupplier_fid(suppiler);
            challan.setAmount(Float.parseFloat(desObjChallan.getAmount()));
            challan.setChallan_date(new SimpleDateFormat("yyyy-MM-dd").parse(desObjChallan.getChallan_date()));
            challan.setChallan_no(desObjChallan.getChallan_no());
            challan.setPurchase_date(new Date());
            challan.setQuantity(desObjChallan.getQuantity());

            if (desObjChallan.getPartyChallan_pid() >= 0)
                challan.setPartyChallan_pid(desObjChallan.getPartyChallan_pid());

            Challan savedChallan = challanRepo.save(challan);

            // setting values in product object
            List<Product> lst_Products = new ArrayList<Product>();

            for (DesObjProduct objproduct : desObjProduct) {
                Product p = new Product();
                Optional<Category> categoryoptional = categoryRepo.findById(objproduct.getCategory_fid());
                Category category = categoryoptional.orElseThrow(() -> new RuntimeException("Category not found"));

                Optional<Brand> brandoptional = brandRepo.findById(objproduct.getBrand_fid());
                Brand brand = brandoptional.orElseThrow(() -> new RuntimeException("Brand not found"));

                if (objproduct.getProduct_pid() >= 0)
                    p.setProduct_pid(objproduct.getProduct_pid());

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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/master/savebrand")
    public String saveBrand(@RequestParam String brand_pid, @RequestParam String name, @RequestParam String code,
            Model model) {
        Brand brand = new Brand();
        brand.setCode(code);
        brand.setName(name);
        if (!brand_pid.isEmpty())
            brand.setBrand_pid(Integer.parseInt(brand_pid));
        try {
            brandRepo.save(brand);
            model.addAttribute("sucmessage", "Brand Added...");

        } catch (Exception e) {
            // model.addAttribute("errormessage", code + " Already Present..." +
            // e.getMessage());
            model.addAttribute("errormessage", e.getMessage());
            // model.addAttribute("data", brand);

        }
        List<Brand> brands = brandRepo.findAll();
        model.addAttribute("data", brands);
        model.addAttribute("name", "brannd");
        return "/Grid/gid1";
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
    // public ResponseEntity<?> generateInvoice(@RequestBody List<Product> p) {
    public ResponseEntity<?> generateInvoice(@RequestBody DesObjBillProduct billProduct, Principal principal) {
        try {

            List<Product> productList = billProduct.getProduct();

            List<String> errorList = invoiceService.validateinvoice(productList);

            if (errorList.size() > 0) {
                return new ResponseEntity<>(errorList, HttpStatus.NOT_FOUND);
            }
            Bill bill = billProduct.getBill();
            bill.setDate(new Date());
            try {
                Customer customer = customerRepo.findById(Integer.parseInt(bill.get_customer_fid())).orElseThrow();
                bill.setCustomer_fid(customer);
            } catch (Exception e) {

            }
            // User user=this.userRepo.getUserByUserName(principal.getName());

            Bill bill2 = billRepo.save(bill);

            boolean updateStatus = invoiceService.updateProduct(productList, bill2);
            // List<BillDetails> billDetails = billDetailRepo.getBillDetailsByBill_fid(bill2);
            // Map<String, Object> resMap = new HashMap<>();
            // resMap.put("BIll", bill2);
            // resMap.put("BillDetails", billDetails);

            if (updateStatus) {
                var invoicehtml=invoiceService.printIncoice(bill2);
                return new ResponseEntity<>(invoicehtml, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }
        return new ResponseEntity<>("Something Went Wrong....", HttpStatus.CONFLICT);

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
                    //user.setImageUrl("/images/" + timestamp + file.getOriginalFilename());


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
    public ResponseEntity<?> savePO(@RequestParam("file") MultipartFile file, 
    @RequestParam String ponumber,
            @RequestParam String supplier_fid,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date poDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date podueDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date poreceivedDate,

            @RequestParam String amt,
            @RequestParam String qty, @RequestParam String note) {
        try {

            byte[] attchment = file.getInputStream().readAllBytes();
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
            var receiver = suppiler.getEmail();
            var subject = "Purchase Order ";
            var filename = file.getOriginalFilename();
            StringBuilder body_Builder = new StringBuilder();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            body_Builder.append("Dear " + suppiler.getName() + ",\r\n" + //
                    "\r\n" +
                    "I trust this email finds you well. We are excited to place a purchase order with your company for the following items:\r\n"
                    + //
                    "Total Estimated Purchase Order Quantity : " + order.getQuantity() +
                    "Total Estimated Purchase Order Cost: " + order.getAmount() + //
                    "\r\n" + //
                    "Please find attached the detailed Purchase Order document containing specifications, terms, and conditions. Kindly review the document thoroughly, and confirm your acceptance at your earliest convenience.\r\n"
                    + //
                    "\r\n" + //
                    "Attachment: [Attach the Purchase Order document]\r\n" +
                    "\r\n" +
                    "Delevery Note : " + order.getNotes() +
                    "\r\n" + //

                    "Delivery Date : " + format.format(order.getDueDate()) + "\r\n" + //
                    "Delivery Location: [Provide the delivery address]\r\n" + //
                    "Should there be any discrepancies or if you require further details, please do not hesitate to contact us promptly.\r\n"
                    + //
                    "\r\n" + //
                    "Your swift attention to this matter is highly appreciated. We anticipate a successful collaboration and thank you for your cooperation.\r\n"
                    + //
                    "\r\n" + //
                    "Best regards,\r\n" + //
                    "\r\n" + //
                    "[Your Full Name]\r\n" + //
                    "[Your Position]\r\n" + //
                    "[Your Company Name]\r\n" + //
                    "[Your Contact Information]");
            // body_Builder.append("<p>Dear "+suppiler.getName()+",\r\n </p>"+
            // "<p>I trust this email finds you well. We are delighted to inform you that we
            // have decided to proceed with a purchase order from your esteemed company. The
            // details are as follows:</p>"
            // +"<p><strong>Total Estimated Purchase Order Quantity:</strong>
            // "+order.getQuantity()+"</p>"
            // +"<p><strong>Total Estimated Purchase Order Cost:</strong>
            // "+order.getAmount()+"</p>"
            // +"<p>Please refer to the attached Purchase Order document for a comprehensive
            // overview of the specifications, terms, and conditions associated with this
            // order.</p>"

            // +"<p><strong>Delivery Note:</strong> "+order.getNotes()+"</p>"
            // +"<p><strong>Delivery Date:</strong>"+order.getDueDate()+"</p>"
            // +"<p>We kindly request you to review the attached document thoroughly and
            // confirm your acceptance at your earliest convenience.</p><p>Should there be
            // any discrepancies or if you require further clarification on any aspect of
            // the purchase order, please do not hesitate to reach out to us promptly. Your
            // prompt attention to this matter is highly appreciated.</p><p>We look forward
            // to a successful collaboration and express our gratitude for your cooperation
            // in advance.</p><p><strong>Best regards,</strong></p><p>[Your Full
            // Name]<br>[Your Position]<br>[Your Company Name]<br>[Your Contact
            // Information]</p>");
            

            // var isSendmail = emailHelper.sendMail(subject, "body_Builder".toString(),
            // attchment, receiver, filename);
            String result = emailService.sendMail(file, suppiler.getEmail(), null, subject, body_Builder.toString());

            // public String sendMail(MultipartFile[] file, String to, String[] cc, String
            // subject, String body) {

            if (result.equals("mail send"))
                return new ResponseEntity<>("Purchase Order Generated Succesfull...", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Something Went Wrong...", HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>("Something Went Wrong...", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/contactus")
    public String postMethodName(@RequestParam String name,@RequestParam String mobile, @RequestParam String Email,@RequestParam String body,Model model) {
        
        String emailbody="Dear sir,\r\n" + //
                        "\r\n" + //
                        "I hope this email finds you well. My name is "+name+", and I am reaching out to request contact from your team regarding inquiry"+"\r\n\r\n" + //
                           body+"\r\n"    +
                           "Please kindly arrange for someone from your team to contact me at your earliest convenience. You can reach me via email at "+Email+" or by phone at "+mobile+". \r\n\r\n"+//
"Thank you very much for your attention to this request. I look forward to hearing from you soon." + //
        "\r\n" + //
                        "Warm regards,\r\n" + //
                                                        "\r\n" + //
                                                        name+"\r\n" + //                                                        
                                                        mobile;

        String result = emailService.sendMail(null, "shaileshmore500@gmail.com", null, "Request for Contact", emailbody);

            // public String sendMail(MultipartFile[] file, String to, String[] cc, String
            // subject, String body) {

            if (result.equals("mail send"))
                model.addAttribute("status","success");
                else
                model.addAttribute("status","error");

        
                return "forms/contactus";
    }
    
}
