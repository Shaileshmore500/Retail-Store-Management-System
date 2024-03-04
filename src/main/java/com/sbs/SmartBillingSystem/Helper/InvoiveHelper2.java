package com.sbs.SmartBillingSystem.Helper;

import org.springframework.stereotype.Component;

@Component
public class InvoiveHelper2 {
//     @Autowired
//     ProductRepo productRepo;
//     @Autowired
//     BillDetailRepo billDetailRepo;
//     @Autowired
//     BillRepo billRepo;

//     public List<String> validateinvoice(List<Product> products) {
//         List<Product> valid_productList = new ArrayList<>();

//         List<String> errorList = new ArrayList<>();

//         for (Product product : products) {

//             float oldBillQty = 0;
//             if (product.getBillDetails_fid() != null && product.getBillDetails_fid() != "") {
//                 BillDetails billDetails = billDetailRepo.findById(Integer.parseInt(product.getBillDetails_fid())).get();
//                 oldBillQty = billDetails.getQuantity();
//             }

//             float invoiceQTY = product.getQuantity();
//             float availableQTY = oldBillQty
//                     + productRepo.findById(product.getProduct_pid()).orElseThrow().getQuantity();
//             if (invoiceQTY > availableQTY)
//                 errorList.add(product.getQuantity() + " Quantity is not available in " + product.getProduct_pid()
//                         + " product");

//         }

//         return errorList;

//     }

//     public boolean updateProduct(List<Product> p, Bill b) {
//         try {

//             boolean detailstatus = insertInvoiceDetails(p, b);
//             if (!detailstatus)
//                 return detailstatus;

//             // for (Product product : p) {

//             //     float oldBillQty = 0;
//             //     if (product.getBillDetails_fid() != null && product.getBillDetails_fid() != "") {
//             //         BillDetails billDetails = billDetailRepo.findById(Integer.parseInt(product.getBillDetails_fid()))
//             //                 .get();
//             //         oldBillQty = billDetails.getQuantity();
//             //     }

//             //     Product pr = productRepo.findById(product.getProduct_pid()).orElseThrow();
//             //     pr.setQuantity(oldBillQty + pr.getQuantity() - product.getQuantity());
//             //     productRepo.save(pr);

//             // }

//             return true;
//         } catch (Exception e) {
//             return false;

//         }

//     }

//     public boolean insertInvoiceDetails(List<Product> p, Bill bill) {
//         try {

            
// // if any item removed from invoice then we delete from bill detail or update quantity in quantity modified
//             List<BillDetails> lst_billDetails = billDetailRepo.getBillDetailsByBill_fid(bill);
//             if (lst_billDetails.size() > 0) {
                
//                 for (BillDetails billDetails2 : lst_billDetails) {
//                     boolean isPresent = false;
//                     for (Product product : p) {
//                         if (product.getProduct_pid() == billDetails2.getProduct_fid().getProduct_pid()) {
//                             isPresent = true;
//                            if(product.getQuantity()!=billDetails2.getQuantity())
//                            {
//                                var diff=billDetails2.getQuantity()-product.getQuantity();
//                                var actualaproduct=productRepo.findById(billDetails2.getProduct_fid().getProduct_pid()).get();
//                                actualaproduct.setQuantity(actualaproduct.getQuantity()+diff);
//                                productRepo.save(actualaproduct);
//                            }
//                             break;
//                         }
//                     }
//                     if (!isPresent) {
//                         Product removedProduct = productRepo.findById(billDetails2.getProduct_fid().getProduct_pid()).get();
//                         removedProduct.setQuantity(removedProduct.getQuantity() + billDetails2.getQuantity());
//                         productRepo.save(removedProduct);
//                         billDetailRepo.deleteById(billDetails2.getBillDetails_pid());
//                     }

//                 }
//             }

//             float amount = 0;
//             float Quantity = 0;
//             float discount_amount = 0;
//             float net_amount = 0;
//             for (Product product : p) {

//                 BillDetails billDetails = new BillDetails();
//                 billDetails.setBill_fid(bill);
//                 billDetails.setDiscount_amount(product.getDiscountamt());
//                 billDetails.setDiscount_rate(product.getDiscountper());
//                 billDetails.setNet_amount(product.getNetamount());
//                 billDetails.setProduct_fid(product);
//                 billDetails.setQuantity(product.getQuantity());
//                 billDetails.setTotal_amount(product.getTotal_amount());
//                 if (product.getBillDetails_fid() != "" && product.getBillDetails_fid() != null)
//                     billDetails.setBillDetails_pid(Integer.parseInt(product.getBillDetails_fid()));
//                 billDetailRepo.save(billDetails);

//                 Quantity += product.getQuantity();
//                 discount_amount += product.getDiscountamt();
//                 net_amount += product.getNetamount();
//                 amount += product.getTotal_amount();
//             }
//             bill.setDate(java.sql.Date.valueOf(LocalDate.now()));
//             bill.setAmount(amount);
//             bill.setDiscount_amount(discount_amount);
//             bill.setNet_amount(net_amount);

//             bill.setQuantity(Quantity);

//             billRepo.save(bill);

//             return true;
//         } catch (Exception e) {
//             return false;

//         }

//     }
    

}
