package com.sbs.SmartBillingSystem.controllers;

import com.sbs.SmartBillingSystem.Entity.Challan;
import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Repository.ChallanRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ctrService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    ChallanRepo challanRepo;

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

}
