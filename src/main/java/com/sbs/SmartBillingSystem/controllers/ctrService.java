package com.sbs.SmartBillingSystem.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.sbs.SmartBillingSystem.Entity.BillDetails;
import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Challan;
import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Entity.User;
import com.sbs.SmartBillingSystem.Entity.serializedObject.DesObjBillProduct;
import com.sbs.SmartBillingSystem.Entity.serializedObject.DesObjChallan;
import com.sbs.SmartBillingSystem.Repository.BillDetailRepo;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.ChallanRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;
import com.sbs.SmartBillingSystem.Repository.SupplierRepo;
import com.sbs.SmartBillingSystem.Repository.UserRepo;
import com.sbs.SmartBillingSystem.Services.DataImportService;
import com.sbs.SmartBillingSystem.Services.InvoiceService;

import aj.org.objectweb.asm.TypeReference;
import jakarta.mail.Multipart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
public class ctrService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    ChallanRepo challanRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BillDetailRepo billDetailRepo;
    @Autowired
    DataImportService dataImportService;
    @Autowired
    SupplierRepo supplierRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    // @Autowired
    // InvoiveHelper invoiveHelper;
    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/setnewpassword")
    public ResponseEntity<String> setnewpassword(@RequestParam String old, @RequestParam String newpass,
            HttpServletRequest request) {
        try {
            User user = (User) request.getSession().getAttribute("currentUser");
            boolean ismatch = bCryptPasswordEncoder.matches(old, user.getPassword());
            if (ismatch) {
                user.setPassword(bCryptPasswordEncoder.encode(newpass));
                userRepo.save(user);

                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Old Password not Matchhed", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {

            return new ResponseEntity<>("Oops! Something went wrong. Please try again later...",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/printBarcode")
    public ResponseEntity<List<Product>> printbarcode(@RequestBody Map<String, String> requestBody) {
        String challanID = requestBody.get("challanID");
        System.out.println("Challan Id" + Integer.parseInt(challanID));
        // challanRepo.findById(Integer.parseInt(challanID));
        // Challan challan =
        // challanRepo.findById(Integer.parseInt(challanID)).orElse(null);
        // if (challan != null) {
        List<Product> list_product = productRepo.findByChallan_fid(Integer.parseInt(challanID));

        System.out.println("in list");
        // System.out.println(list_product);
        for (Product p : list_product) {
            System.out.println("name:" + p.getName());
            System.out.println("code:" + p.getCode());
            System.out.println("pid:" + p.getProduct_pid());
        }

        return ResponseEntity.ok(list_product);
        // }
        // return ResponseEntity.notFound().build();

    }

    // this is used is invoice page
    @PostMapping("/searchProduct")
    public ResponseEntity<?> serachProductEntity(@RequestBody Map<String, Object> details) {
        try {
            // System.out.println("jsonstring:" + details.get("product"));
            String pid = (String) details.get("id");
            String qty = (String) details.get("qty");
            String existid = (String) details.get("exist_pid");
            String existqty = (String) details.get("exist_qty");
            var billDetail_pid = details.get("billDetail_pid");

            Product product = productRepo.findById(Integer.parseInt(pid)).orElse(null);
            if (product != null) {

                float oldBillQty = 0;
                if (billDetail_pid != null && billDetail_pid != "") {
                    BillDetails billDetails = billDetailRepo.findById(Integer.parseInt(billDetail_pid.toString()))
                            .get();
                    oldBillQty = billDetails.getQuantity();
                }

                float productqty = Float.parseFloat(qty),
                        availableqty = product.getQuantity();

                if (existid != null && existqty != null && existid != "" && existqty != "") {
                    productqty = Float.parseFloat(qty) + Float.parseFloat(existqty);
                    availableqty = product.getQuantity() - Float.parseFloat(existqty);
                }

                if (Float.parseFloat(qty) <= availableqty + oldBillQty) {
                    Product p = new Product();
                    p.setProduct_pid(product.getProduct_pid());
                    p.setStyle(product.getStyle());
                    p.setMrp(product.getMrp());
                    p.setQuantity(productqty);
                    p.setTotal_amount(product.getMrp() * p.getQuantity());
                    p.setNetamount(p.getTotal_amount() - p.getTotal_amount() * product.getDiscountper() / 100);
                    p.setDiscountamt(p.getTotal_amount() * product.getDiscountper() / 100);
                    if (billDetail_pid != null && billDetail_pid != "")
                        p.setBillDetails_fid(billDetail_pid.toString());

                    return new ResponseEntity<>(p, HttpStatus.OK);
                } else {
                    System.out.println("in elsblock");
                    return new ResponseEntity<>(
                            "Available Quantity is Only " + (availableqty + oldBillQty),
                            HttpStatus.NOT_FOUND);
                }

            } else {
                return new ResponseEntity<>("Product Not Found...", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    // @PostMapping("/validateinvoice")
    // public ResponseEntity<?> validateinvoice(@RequestBody List<Product> p) {
    // List<Product> valid_productList = new ArrayList<>();

    // for (Product product : p) {

    // float invoiceQTY = product.getQuantity();
    // float availableQTY =
    // productRepo.findById(product.getProduct_pid()).orElseThrow().getQuantity();
    // if (invoiceQTY > availableQTY)
    // return new ResponseEntity<>(
    // product.getQuantity() + " is not available in " + product.getProduct_pid() +
    // " product",
    // HttpStatus.NOT_FOUND);

    // }

    // return null;

    // }

    // this is used in search product page
    @PostMapping("/getProductByParameter")
    public ResponseEntity<?> getProductByParameter(@RequestBody Map<String, String> jsonObject) {

        try {

            int productPid = 0;
            int categoryID = 0;
            int brandID = 0;
            Brand brand = null;
            Category category = null;

            if (jsonObject.get("id") != null && jsonObject.get("id") != "")
                productPid = Integer.parseInt(jsonObject.get("id"));
            if (jsonObject.get("category") != null && jsonObject.get("category") != "") {
                categoryID = Integer.parseInt(jsonObject.get("category"));
                category = categoryRepo.findById(categoryID).get();
            }
            if (jsonObject.get("brand") != null && jsonObject.get("brand") != "") {
                brandID = Integer.parseInt(jsonObject.get("brand"));
                brand = brandRepo.findById(brandID).get();
            }

            StringBuilder queryStringBuilder = new StringBuilder("SELECT p FROM Product p WHERE 1=1 ");

            if (jsonObject.get("id") != null && jsonObject.get("id") != "") {
                queryStringBuilder.append("AND p.product_pid = :productPid ");
            }
            if (brand != null) {
                queryStringBuilder.append("AND p.brand_fid = :brand ");
            }
            if (category != null) {
                queryStringBuilder.append("AND p.category_fid = :category ");
            }

            TypedQuery<Product> query = entityManager.createQuery(queryStringBuilder.toString(), Product.class);

            if (jsonObject.get("id") != null && jsonObject.get("id") != "") {
                query.setParameter("productPid", productPid);
            }

            if (brand != null) {
                query.setParameter("brand", brand);
            }
            if (category != null) {
                query.setParameter("category", category);
            }
            if (query.getResultList().size() > 0)
                return new ResponseEntity<>(query.getResultList(), HttpStatus.OK);
            else
                return new ResponseEntity<>("Product Not Found...", HttpStatus.NOT_FOUND);

        } catch (Exception e) {

            return new ResponseEntity<>("Products Not Found...", HttpStatus.NOT_FOUND);

        }

    }

    @PostMapping("Create-Payment-Order")
    // public String createPayment(@RequestBody List<Product> productList) throws
    // RazorpayException ,Exception
    public ResponseEntity<String> createPaymentOrder(@RequestBody DesObjBillProduct billProduct) {
        try {

            List<Product> productList = billProduct.getProduct();
            List<String> errorList = invoiceService.validateinvoice(productList);

            if (errorList.size() > 0) {
                return new ResponseEntity<String>(errorList.toString(), HttpStatus.CONFLICT);
            }

            float amount = 0;
            for (Product p : productList) {
                amount += p.getNetamount();

            }

            int amt = (int) amount;
            // Integer.parseInt(data.get("amount").toString());

            var client = new RazorpayClient("rzp_test_2A5WF7VAuSATXf", "apGC4z6uGpKglolXxj0Ahuap");

            JSONObject ob = new JSONObject();
            ob.put("amount", amt * 100);
            ob.put("currency", "INR");
            ob.put("receipt", "txn_235425");

            // creating new order

            Order order = client.Orders.create(ob);
            System.out.println(order);

            // if you want you can save this to your data..
            // return order.toString();
            return new ResponseEntity<String>(order.toString(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Something Went Wrong...", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/challanimport")
    public ResponseEntity<?> challanImport(
            @RequestParam("challan") String JSONchallan,
            @RequestParam("file") MultipartFile file) throws ParseException {

        if (!file.getOriginalFilename().contains("xls") && !file.getOriginalFilename().contains("xlsx"))
            return ResponseEntity.badRequest().body("Please Upload Excel FIle For Importing...");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            DesObjChallan desObjChallan = objectMapper.readValue(JSONchallan, DesObjChallan.class);

            Challan challan = new Challan();
            challan.setSupplier_fid(supplierRepo.findById(Integer.parseInt(desObjChallan.getSupplier_fid())).get());
            challan.setAmount(Float.parseFloat(desObjChallan.getAmount()));
            challan.setChallan_date(new SimpleDateFormat("yyyy-MM-dd").parse(desObjChallan.getChallan_date()));
            challan.setChallan_no(desObjChallan.getChallan_no());
            challan.setPurchase_date(new Date());
            challan.setQuantity(desObjChallan.getQuantity());
            if (desObjChallan.getPartyChallan_pid() >= 0)
            challan.setPartyChallan_pid(desObjChallan.getPartyChallan_pid());

            
            Challan savedChallan = challanRepo.save(challan);
            dataImportService.importFromExcel(file, savedChallan);

            

            return ResponseEntity.ok("Data Imported successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Internal Server Error ");

        }

    }

}
