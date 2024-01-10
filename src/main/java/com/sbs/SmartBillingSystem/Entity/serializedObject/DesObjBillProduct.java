package com.sbs.SmartBillingSystem.Entity.serializedObject;

import java.util.List;

import com.sbs.SmartBillingSystem.Entity.Bill;
import com.sbs.SmartBillingSystem.Entity.Product;

public class DesObjBillProduct {
    private List<Product> product;
    private DesObjBill desObjBill;

    public DesObjBillProduct() {
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public DesObjBill getDesObjBill() {
        return desObjBill;
    }

    public void setDesObjBill(DesObjBill desObjBill) {
        this.desObjBill = desObjBill;
    }

}
