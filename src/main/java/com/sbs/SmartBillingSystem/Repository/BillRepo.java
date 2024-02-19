package com.sbs.SmartBillingSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sbs.SmartBillingSystem.Entity.Bill;

public interface BillRepo extends JpaRepository<Bill, Integer> {

//     @Query("SELECT CONCAT(FUNCTION('MONTHNAME', b.date), ' ', FUNCTION('YEAR', b.date)) AS month_year, " +
//            "SUM(b.netAmount) AS totalSales " +
//            "FROM Bill b " +
//            "WHERE YEAR(b.date) = :year " +
//            "GROUP BY MONTH(b.date), YEAR(b.date) " +
//            "ORDER BY YEAR(b.date), MONTH(b.date)")
//    List<Object[]> findMonthlySalesByYear(int year);

}
