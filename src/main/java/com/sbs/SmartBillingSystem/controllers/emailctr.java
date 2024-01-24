package com.sbs.SmartBillingSystem.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sbs.SmartBillingSystem.Entity.PurchaseOrder;
import com.sbs.SmartBillingSystem.Entity.Suppiler;
import com.sbs.SmartBillingSystem.Helper.EmailHelper;
import com.sbs.SmartBillingSystem.Repository.PurchaseOrderRepo;
import com.sbs.SmartBillingSystem.Repository.SupplierRepo;

@Controller
public class emailctr {


 

    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    PurchaseOrderRepo purchaseOrderRepo;






    // @PostMapping("/savePO")
    // public ResponseEntity<?> savePO(@RequestParam("file") MultipartFile file, @RequestParam String ponumber,
    //         @RequestParam String supplier_fid,
    //         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date poDate,
    //         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date podueDate,
    //         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date poreceivedDate,

    //         @RequestParam String amt,
    //         @RequestParam String qty, @RequestParam String note) {
        // try {
        //     byte[] attchment = file.getInputStream().readAllBytes();
        //     PurchaseOrder order = new PurchaseOrder();
        //     Suppiler suppiler = supplierRepo.findById(Integer.parseInt(supplier_fid)).orElseThrow();
        //     order.setSuppiler_fid(suppiler);
        //     order.setAmount(Double.parseDouble(amt));
        //     order.setQuantity(Double.parseDouble(qty));
        //     order.setNotes(note);
        //     order.setCreatedDate(poDate);
        //     order.setDueDate(podueDate);
        //     order.setReceivedDate(poreceivedDate);
        //     order.setAttchmentByte(attchment);
        //     order.setFileName(file.getOriginalFilename());
        //     purchaseOrderRepo.save(order);
        //     var receiver = suppiler.getEmail();
        //     var subject = "Purchase Order - [Your Company Name]";
        //     var filename = file.getOriginalFilename();
        //     // StringBuilder body_Builder=new StringBuilder();

        //     // body_Builder.append("Dear "+suppiler.getName()+",\r\n" + //
        //     // "\r\n" + //
        //     // "I trust this email finds you well. We are excited to place a purchase order
        //     // with your company for the following items:\r\n" + //

        //     // "Total Estimated Purchase Order Cost: [Sum of all estimated costs]\r\n" + //
        //     // "\r\n" + //
        //     // "Please find attached the detailed Purchase Order document containing
        //     // specifications, terms, and conditions. Kindly review the document thoroughly,
        //     // and confirm your acceptance at your earliest convenience.\r\n" + //
        //     // "\r\n" + //
        //     // "Attachment: [Attach the Purchase Order document]\r\n" + //
        //     // "\r\n" + //
        //     // "Delevery Note : "+order.getNotes()+
        //     // "\r\n" + //

        //     // "Delivery Date : "+order.getDueDate()+"\r\n" + //
        //     // "Delivery Location: [Provide the delivery address]\r\n" + //
        //     // "Should there be any discrepancies or if you require further details, please
        //     // do not hesitate to contact us promptly.\r\n" + //
        //     // "\r\n" + //
        //     // "Your swift attention to this matter is highly appreciated. We anticipate a
        //     // successful collaboration and thank you for your cooperation.\r\n" + //
        //     // "\r\n" + //
        //     // "Best regards,\r\n" + //
        //     // "\r\n" + //
        //     // "[Your Full Name]\r\n" + //
        //     // "[Your Position]\r\n" + //
        //     // "[Your Company Name]\r\n" + //
        //     // "[Your Contact Information]");
        //     EmailHelper emailHelper=new EmailHelper();
           
        //     var isSendmail = emailHelper.sendMail(subject, "body_Builder".toString(), attchment, receiver, filename);
        //     if (isSendmail)
        //         return new ResponseEntity<>("Purchase Order Generated Succesfull...", HttpStatus.OK);

        // } catch (Exception e) {
        //     return new ResponseEntity<>("Something Went Wrong...", HttpStatus.BAD_REQUEST);

        // }

        // return new ResponseEntity<>("Something Went Wrong...", HttpStatus.BAD_REQUEST);
    //}
    
}
