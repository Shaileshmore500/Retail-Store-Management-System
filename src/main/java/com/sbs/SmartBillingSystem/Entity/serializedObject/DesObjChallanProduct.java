package com.sbs.SmartBillingSystem.Entity.serializedObject;

import java.util.List;

public class DesObjChallanProduct {
    public List<DesObjProduct> desObjProduct;

    public DesObjChallan desObjChallan;

    public List<DesObjProduct> getDesObjProduct() {
        return desObjProduct;
    }

    public DesObjChallan getDesObjChallan() {
        return desObjChallan;
    }

    public void setDesObjProduct(List<DesObjProduct> desObjProduct) {
        this.desObjProduct = desObjProduct;
    }

    public void setDesObjChallan(DesObjChallan desObjChallan) {
        this.desObjChallan = desObjChallan;
    }

    public DesObjChallanProduct() {
    }

}