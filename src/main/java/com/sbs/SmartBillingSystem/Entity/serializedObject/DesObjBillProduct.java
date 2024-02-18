package com.sbs.SmartBillingSystem.Entity.serializedObject;

import java.util.List;

import com.sbs.SmartBillingSystem.Entity.Bill;
import com.sbs.SmartBillingSystem.Entity.Product;

public class DesObjBillProduct {
    private List<Product> product;
    private Bill bill;
    

    public DesObjBillProduct() {
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

   
}
