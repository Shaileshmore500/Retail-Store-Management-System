package com.sbs.SmartBillingSystem.Services.ServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.sbs.SmartBillingSystem.Entity.Brand;
import com.sbs.SmartBillingSystem.Entity.Category;
import com.sbs.SmartBillingSystem.Entity.Challan;
import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Repository.BrandRepo;
import com.sbs.SmartBillingSystem.Repository.CategoryRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;
import com.sbs.SmartBillingSystem.Services.DataImportService;
import jakarta.mail.internet.MimeMessage;

@Service
public class DataImportServiceImpl implements DataImportService{

    @Autowired
    ProductRepo  productRepo;
    @Autowired
    BrandRepo brandRepo;
    @Autowired 
    CategoryRepo categoryRepo;
    @Override
    public List<Product> importFromExcel(MultipartFile file,Challan challan) throws IOException {
       
      
        List<Product> lst_product=new ArrayList<>();
        Workbook workbook=new XSSFWorkbook(file.getInputStream());
        Sheet sheet=workbook.getSheetAt(0);
        for(Row row : sheet)
        {
            if(row.getRowNum()==0)
            continue;
            Product product=new Product();
            Brand brand=brandRepo.findById((int) row.getCell(2).getNumericCellValue()).get();
            Category category=categoryRepo.findById((int) row.getCell(3).getNumericCellValue()).get();
            product.setCode(row.getCell(0).getStringCellValue());
            product.setName(row.getCell(1).getStringCellValue());
            product.setBrand_fid(brand); 
            product.setCategory_fid(category); 
            product.setMrp((Float.parseFloat(row.getCell(4).getStringCellValue())));
            product.setQuantity((Float.parseFloat(row.getCell(5).getStringCellValue())));
            product.setPurchase_rate((Float.parseFloat(row.getCell(6).getStringCellValue())));
            product.setSize((Float.parseFloat(row.getCell(7).getStringCellValue())));
            product.setTotal_amount(product.getQuantity()*product.getPurchase_rate());
            product.setStyle(challan.getSupplier_fid().getCode().toString().substring(0, Math.min(challan.getSupplier_fid().getCode().length(), 3)) + "-"
            + brand.getCode().substring(0, Math.min(brand.getCode().length(), 3)) + "-"
            + category.getCode().substring(0, Math.min(category.getCode().length(), 3))
            + "#" + product.getSize());
            product.setCreatedDate(LocalDate.now());
            lst_product.add(product);

        }
        productRepo.saveAll(lst_product);


workbook.close();
        return lst_product;
    }
}
