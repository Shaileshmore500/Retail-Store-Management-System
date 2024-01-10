package com.sbs.SmartBillingSystem.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private int purchaseOrder_pid;
    @OneToOne
    private Suppiler suppiler_fid;
    private Date createdDate;
    private Date dueDate;
    private Date receivedDate;
    private String status;
    private String fileName;  
    @Lob  
    private byte[] attchmentByte;
    private String notes;
    private double quantity;
    private Double amount;
    
    public PurchaseOrder() {
    }
    public int getPurchaseOrder_pid() {
        return purchaseOrder_pid;
    }
    public void setPurchaseOrder_pid(int purchaseOrder_pid) {
        this.purchaseOrder_pid = purchaseOrder_pid;
    }
    public Suppiler getSuppiler_fid() {
        return suppiler_fid;
    }
    public void setSuppiler_fid(Suppiler suppiler_fid) {
        this.suppiler_fid = suppiler_fid;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public Date getReceivedDate() {
        return receivedDate;
    }
    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public byte[] getAttchmentByte() {
        return attchmentByte;
    }
    public void setAttchmentByte(byte[] attchmentByte) {
        this.attchmentByte = attchmentByte;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }




    
}
