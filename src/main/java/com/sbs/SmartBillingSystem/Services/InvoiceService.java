package com.sbs.SmartBillingSystem.Services;

import java.util.List;

import com.sbs.SmartBillingSystem.Entity.Bill;
import com.sbs.SmartBillingSystem.Entity.Product;

public interface InvoiceService {

    public boolean updateProduct(List<Product> p, Bill b);
    public List<String> validateinvoice(List<Product> products);
    public boolean insertInvoiceDetails(List<Product> p, Bill bill);
    public String printIncoice(Bill bill);

}
