package com.sbs.SmartBillingSystem.Helper;

import com.sbs.SmartBillingSystem.Entity.Bill;
import com.sbs.SmartBillingSystem.Entity.BillDetails;
import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Repository.BillDetailRepo;
import com.sbs.SmartBillingSystem.Repository.BillRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;
import com.sbs.SmartBillingSystem.Repository.UserRepo;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiveHelper {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    BillDetailRepo billDetailRepo;
    @Autowired
    BillRepo billRepo;

    public List<String> validateinvoice(List<Product> products) {
        List<Product> valid_productList = new ArrayList<>();

        List<String> errorList = new ArrayList<>();

        for (Product product : products) {

            float invoiceQTY = product.getQuantity();
            float availableQTY = productRepo.findById(product.getProduct_pid()).orElseThrow().getQuantity();
            if (invoiceQTY > availableQTY)
                errorList.add(product.getQuantity() + " Quantity is not available in " + product.getProduct_pid()
                        + " product");

        }

        return errorList;

    }

    public boolean updateProduct(List<Product> p, Bill b) {
        try {

            boolean detailstatus = insertInvoiceDetails(p, b);
            if (!detailstatus)
                return detailstatus;

            for (Product product : p) {
                Product pr = productRepo.findById(product.getProduct_pid()).orElseThrow();
                pr.setQuantity(pr.getQuantity() - product.getQuantity());
                productRepo.save(pr);

            }

            return true;
        } catch (Exception e) {
            return false;

        }

    }

    public boolean insertInvoiceDetails(List<Product> p, Bill bill) {
        try {
            float amount = 0;
            float Quantity = 0;
            float discount_amount = 0;
            float net_amount = 0;
            for (Product product : p) {

                BillDetails billDetails = new BillDetails();
                billDetails.setBill_fid(bill);
                billDetails.setDiscount_amount(product.getDiscountamt());
                billDetails.setDiscount_rate(product.getDiscountper());
                billDetails.setNet_amount(product.getNetamount());
                billDetails.setProduct_fid(product);
                billDetails.setQuantity(product.getQuantity());
                billDetailRepo.save(billDetails);

                Quantity += product.getQuantity();
                discount_amount += product.getDiscountamt();
                net_amount += product.getNetamount();
                amount += product.getTotal_amount();
            }
            bill.setDate(java.sql.Date.valueOf(LocalDate.now()));
            bill.setAmount(amount);
            bill.setDiscount_amount(discount_amount);
            bill.setNet_amount(net_amount);
            bill.setQuantity(Quantity);

            billRepo.save(bill);

            return true;
        } catch (Exception e) {
            return false;

        }

    }

}
