package com.sbs.SmartBillingSystem.Services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sbs.SmartBillingSystem.Entity.Challan;
import com.sbs.SmartBillingSystem.Entity.Product;

public interface DataImportService {
       List<Product> importFromExcel(MultipartFile file,Challan challan) throws IOException;
}
