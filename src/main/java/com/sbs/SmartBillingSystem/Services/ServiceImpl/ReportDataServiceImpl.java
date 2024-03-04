package com.sbs.SmartBillingSystem.Services.ServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sbs.SmartBillingSystem.Services.ReportDataService;

@Service
public class ReportDataServiceImpl implements ReportDataService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<Object[]> getMonthlySale(int year) {
       
        try {
            var sql="SELECT  month(date)AS month_year,\n" + //
                       "       SUM(net_amount) AS total_sales\n" + //
                       "FROM retailstoremanagementsystem.bill\n" + //
                       "where year(date)=?\n" + //
                       "GROUP BY DATE_FORMAT(date, '%Y-%m')\n" + //
                       "ORDER BY DATE_FORMAT(date, '%Y-%m');";
                       //String sql = "SELECT month_year, total_sales FROM sales_data WHERE year = ?";
                      sql = "SELECT month_year, total_sales FROM sales_data WHERE year = ?";
 year = 2023;


 List<Object[]> results = jdbcTemplate.query(sql, new RowMapper<Object[]>() {
    @Override
    public Object[] mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        int monthYear = resultSet.getInt("month_year");
        double totalSales = resultSet.getDouble("total_sales");
        return new Object[]{monthYear, totalSales};
    }
});
                       
return results;


         
         }catch (Exception e)
         {
             System.out.println(e.getMessage());
         }
return null;


    }

    
}
