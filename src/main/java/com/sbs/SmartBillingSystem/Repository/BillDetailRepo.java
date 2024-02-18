package com.sbs.SmartBillingSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sbs.SmartBillingSystem.Entity.Bill;
import com.sbs.SmartBillingSystem.Entity.BillDetails;

import jakarta.transaction.Transactional;

public interface BillDetailRepo extends JpaRepository<BillDetails, Integer> {

    @Query("select bd from BillDetails bd where bd.bill_fid = :bill")
    List<BillDetails> getBillDetailsByBill_fid(Bill bill);
    //@Query("delete bd from BillDetails bd where bd.bill_fid = :bill")
    @Transactional
    @Modifying
    @Query("delete from BillDetails bd where bd.bill_fid = :bill")
    void deleteByBill(Bill bill);

}
