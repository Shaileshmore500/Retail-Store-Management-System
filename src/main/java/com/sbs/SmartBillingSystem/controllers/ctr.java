package com.sbs.SmartBillingSystem.controllers;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.SmartBillingSystem.Repository.AttendenceRepo;
import com.sbs.SmartBillingSystem.Repository.BillDetailRepo;
import com.sbs.SmartBillingSystem.Repository.BillRepo;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.ChallanRepo;
import com.sbs.SmartBillingSystem.Repository.CustomerRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;
import com.sbs.SmartBillingSystem.Repository.PurchaseOrderRepo;
import com.sbs.SmartBillingSystem.Repository.SupplierRepo;
import com.sbs.SmartBillingSystem.Repository.UserRepo;
import com.sbs.SmartBillingSystem.Services.ReportDataService;
import com.sbs.SmartBillingSystem.Entity.*;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Map;

@Controller
public class ctr {
    @Autowired
    UserRepo userRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    ChallanRepo challanRepo;
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
    @Autowired
    ReportDataService dataService;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AttendenceRepo attendenceRepo;
    @Autowired
    PurchaseOrderRepo orderRepo;

    // if u want to use method base authorization then use this anotation
    // @PreAuthorize("hasRoll('ADMIN')")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(Principal principal, HttpServletRequest request, Model model) {

        var a = principal.getName();
       
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
           // product.setName("product 1");
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
        model.addAttribute("list_product", productRepo.findAll());

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
    public String attendance(Model model) {

        var a = attendenceRepo.getAttendenceByDate();
        int totaldays = a.size() > 0 ? a.get(0).getMonthMaster_fid().getTotalDays() : 30;
        String monthName = a.size() > 0 ? a.get(0).getMonthMaster_fid().getName() : "";

        model.addAttribute("at", a);
        model.addAttribute("totaldays", totaldays);
        model.addAttribute("monthName", monthName);

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
            model.addAttribute("data",productRepo.findAll());

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
            
            // List<Object> list=new ArrayList<>();
            // for(Brand b : brands)
            // {Map<String,Object> map=new HashMap<>();
            //     map.put("Brand ID", b.getBrand_pid());
            //     map.put("Brand Name", b.getName());
            //     map.put("Brand Code", b.getCode());
            //     list.add(map);
            // }



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

        }else if (form.equals("po")) {
            model.addAttribute("data", orderRepo.findAll());
            model.addAttribute("name", form);
            

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
    @GetMapping("/contactus")
    public String contactus() {
        return "forms/contactus";
    }
    @GetMapping("/reports")
    public String reports(Model model) {
        
        List<Map<String, Object>> monthlySale = new ArrayList<>();
        List<Map<String, Object>> monthlyPurchase = new ArrayList<>();
        List<Map<String, Object>> StockBrandwise = new ArrayList<>();
        List<Map<String, Object>> StockCategorywise = new ArrayList<>();
        List<Map<String, Object>> ProfitLoss = new ArrayList<>();
        try {
            var sql_MOnthlySale = "SELECT  month(date) AS month_year, SUM(net_amount) AS total_sales FROM retailstoremanagementsystem.bill where year(date)=year(now()) GROUP BY DATE_FORMAT(date, '%Y-%m') ORDER BY DATE_FORMAT(date, '%Y-%m');";
            var sql_MonthlyPurchase = "SELECT  month(created_date) AS month_year, SUM(amount) AS total_purchase \n" + //
                    "FROM retailstoremanagementsystem.purchase_order where year(created_date)=year(now()) \n" + //
                    "GROUP BY DATE_FORMAT(created_date, '%Y-%m') \n" + //
                    "ORDER BY DATE_FORMAT(created_date, '%Y-%m');";
            var sql_StockBrandwise = "select brand.name as brandname , sum(quantity) as qty from retailstoremanagementsystem.product\n"
                    + //
                    "left join retailstoremanagementsystem.brand on brand_fid_brand_pid=brand_pid\n" + //
                    "group by brand_fid_brand_pid";

            var sql_StockCategoryWise = "select category.name as categoryname , sum(quantity) as qty from retailstoremanagementsystem.product left join retailstoremanagementsystem.category on category_fid_category_pid=category_pid  group by category_fid_category_pid";
var sql_ProfitLoss="\n" + //
        " SELECT  month(date) as month,sum(bd.net_amount-(bd.quantity*p.purchase_rate)) as profit FROM retailstoremanagementsystem.bill b\n" + //
        "left join     retailstoremanagementsystem.bill_details bd on b.bill_pid=bd.bill_fid_bill_pid\n" + //
        "left JOIN   retailstoremanagementsystem.Product p ON bd.product_fid_product_pid = p.product_pid    group by month(date)    order  by month(date)\n" ;

            monthlySale = jdbcTemplate.query(sql_MOnthlySale, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    int monthYear = resultSet.getInt("month_year");
                    double totalSales = resultSet.getDouble("total_sales");

                    Map<String, Object> saleMap = new HashMap<>();
                    saleMap.put("monthYear", monthYear);
                    saleMap.put("totalSales", totalSales);

                    return saleMap;
                }
            });

            monthlyPurchase = jdbcTemplate.query(sql_MonthlyPurchase, new RowMapper<Map<String, Object>>() {

                @Override
                public Map<String, Object> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    int monthYear = resultSet.getInt("month_year");
                    double total_purchase = resultSet.getDouble("total_purchase");

                    Map<String, Object> m = new HashMap<>();
                    m.put("monthYear", monthYear);
                    m.put("total_purchase", total_purchase);
                    return m;

                }

            });

            StockBrandwise = jdbcTemplate.query(sql_StockBrandwise, new RowMapper<Map<String, Object>>() {

                @Override
                public Map<String, Object> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    var brandname = resultSet.getString("brandname");
                    double qty = resultSet.getDouble("qty");

                    Map<String, Object> m = new HashMap<>();
                    m.put("brandname", brandname);
                    m.put("qty", qty);
                    return m;

                }

            });

            StockCategorywise = jdbcTemplate.query(sql_StockCategoryWise, new RowMapper<Map<String, Object>>() {

                @Override
                public Map<String, Object> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    var categoryname = resultSet.getString("categoryname");
                    double qty = resultSet.getDouble("qty");

                    Map<String, Object> m = new HashMap<>();
                    m.put("categoryname", categoryname);
                    m.put("qty", qty);
                    return m;

                }

            });

            ProfitLoss = jdbcTemplate.query(sql_ProfitLoss, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    int monthYear = resultSet.getInt("month");
                    double profit = resultSet.getDouble("profit");

                    Map<String, Object> m = new HashMap<>();
                    m.put("monthYear", monthYear);
                    m.put("profit", profit);

                    return m;
                }
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        model.addAttribute("monthlySale", monthlySale);
        model.addAttribute("monthlyPurchase", monthlyPurchase);
        model.addAttribute("StockBrandwise", StockBrandwise);
        model.addAttribute("StockCategorywise", StockCategorywise);
        model.addAttribute("ProfitLoss", ProfitLoss);



        return "/report";
    }
}
