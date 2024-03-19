package com.sbs.SmartBillingSystem.Services.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.SmartBillingSystem.Entity.Bill;
import com.sbs.SmartBillingSystem.Entity.BillDetails;
import com.sbs.SmartBillingSystem.Entity.Product;
import com.sbs.SmartBillingSystem.Repository.BillDetailRepo;
import com.sbs.SmartBillingSystem.Repository.BillRepo;
import com.sbs.SmartBillingSystem.Repository.ProductRepo;
import com.sbs.SmartBillingSystem.Services.InvoiceService;

@Service
public class InvoiceServiceImp  implements InvoiceService{

   @Autowired
    ProductRepo productRepo;
    @Autowired
    BillDetailRepo billDetailRepo;
    @Autowired
    BillRepo billRepo;

    @Override   
    public List<String> validateinvoice(List<Product> products) {
        List<Product> valid_productList = new ArrayList<>();

        List<String> errorList = new ArrayList<>();

        for (Product product : products) {

            float oldBillQty = 0;
            if (product.getBillDetails_fid() != null && product.getBillDetails_fid() != "") {
                BillDetails billDetails = billDetailRepo.findById(Integer.parseInt(product.getBillDetails_fid())).get();
                oldBillQty = billDetails.getQuantity();
            }

            float invoiceQTY = product.getQuantity();
            float availableQTY = oldBillQty
                    + productRepo.findById(product.getProduct_pid()).orElseThrow().getQuantity();
            if (invoiceQTY > availableQTY)
                errorList.add(product.getQuantity() + " Quantity is not available in " + product.getProduct_pid()
                        + " product");

        }

        return errorList;

    }

    @Override
    public boolean updateProduct(List<Product> p, Bill b) {
        try {

            boolean detailstatus = insertInvoiceDetails(p, b);
            if (!detailstatus)
                return detailstatus;

            /*
             for (Product product : p) {
                 float oldBillQty = 0;
                 if (product.getBillDetails_fid() != null && product.getBillDetails_fid() != "") {
                     BillDetails billDetails = billDetailRepo.findById(Integer.parseInt(product.getBillDetails_fid()))
                             .get();
                     oldBillQty = billDetails.getQuantity();
                 }
                 Product pr = productRepo.findById(product.getProduct_pid()).orElseThrow();
                 pr.setQuantity(oldBillQty + pr.getQuantity() - product.getQuantity());
                 productRepo.save(pr);
             }
            */

            return true;
        } catch (Exception e) {
            return false;

        }

    }
@Override
    public boolean insertInvoiceDetails(List<Product> p, Bill bill) {
        try {

            
// if any item removed from invoice then we delete from bill detail or update quantity in quantity modified
            List<BillDetails> lst_billDetails = billDetailRepo.getBillDetailsByBill_fid(bill);
            if (lst_billDetails.size() > 0) {
                
                for (BillDetails billDetails2 : lst_billDetails) {
                    boolean isPresent = false;
                    for (Product product : p) {
                        if (product.getProduct_pid() == billDetails2.getProduct_fid().getProduct_pid()) {
                            isPresent = true;
                           if(product.getQuantity()!=billDetails2.getQuantity())
                           {
                               var diff=billDetails2.getQuantity()-product.getQuantity();
                               var actualaproduct=productRepo.findById(billDetails2.getProduct_fid().getProduct_pid()).get();
                               actualaproduct.setQuantity(actualaproduct.getQuantity()+diff);
                               productRepo.save(actualaproduct);
                           }
                            break;
                        }
                    }
                    if (!isPresent) {
                        Product removedProduct = productRepo.findById(billDetails2.getProduct_fid().getProduct_pid()).get();
                        removedProduct.setQuantity(removedProduct.getQuantity() + billDetails2.getQuantity());
                        productRepo.save(removedProduct);
                        billDetailRepo.deleteById(billDetails2.getBillDetails_pid());
                    }

                }
            }
            else{

            float amount = 0;
            float Quantity = 0;
            float discount_amount = 0;
            float net_amount = 0;
            for (Product product : p) {

                BillDetails billDetails = new BillDetails();
                billDetails.setBill_fid(bill);
                billDetails.setDiscount_amount(product.getDiscountamt());
                billDetails.setDiscount_rate(product.getDiscountper());
                billDetails.setNet_amount(product.getNetamount());
                billDetails.setProduct_fid(product);
                billDetails.setQuantity(product.getQuantity());
                billDetails.setTotal_amount(product.getTotal_amount());
                if (product.getBillDetails_fid() != "" && product.getBillDetails_fid() != null)
                    billDetails.setBillDetails_pid(Integer.parseInt(product.getBillDetails_fid()));
                billDetailRepo.save(billDetails);


                var actualaproduct=productRepo.findById(product.getProduct_pid()).get();
                actualaproduct.setQuantity(actualaproduct.getQuantity()-product.getQuantity());
                productRepo.save(actualaproduct);




                Quantity += product.getQuantity();
                discount_amount += product.getDiscountamt();
                net_amount += product.getNetamount();
                amount += product.getTotal_amount();
            }
            bill.setDate(java.sql.Date.valueOf(LocalDate.now()));
            bill.setAmount(amount);
            bill.setDiscount_amount(discount_amount);
            bill.setNet_amount(net_amount);

            bill.setQuantity(Quantity);

            billRepo.save(bill);
        }
            return true;
        } catch (Exception e) {
            return false;

        }

    }

@Override
public String printIncoice(Bill bill) {



    List<BillDetails> billDetails= billDetailRepo.getBillDetailsByBill_fid(bill);
  StringBuilder builder=new StringBuilder();
  try {
      var name=bill.getCustomer_fid() ==null ? "" :bill.getCustomer_fid().getName();
      var mobile=bill.getCustomer_fid()==null ? "" : bill.getCustomer_fid().getMobile_no();
      
//   builder.append("<!DOCTYPE html>  <html lang='en'> <head>      <meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>      <title>Document</title>");
// builder.append("<style>@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Protest+Guerrilla&display=swap');\r\n" + //
//         "body {\r\n" + //
//         "    background: beige;\r\n" + //
//         "    font-family: \"Poppins\";\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         ".invoice_modal {\r\n" + //
//         "    width: 900px;\r\n" + //
//         "    margin-top: 50px;\r\n" + //
//         "    margin: 0 auto;\r\n" + //
//         "    background: beige;\r\n" + //
//         "    font-family: \"Poppins\";\r\n" + //
//         "    border: 2px solid;\r\n" + //
//         "padding: 7px;\r\n" + //
//         "border: 1px solid;\r\n" + //
//         "padding:7px ;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         ".invoice_banner {\r\n" + //
//         "    /* min-height: 200px; */\r\n" + //
//         "    height: 50px;\r\n" + //
//         "    background-color: #02090a;\r\n" + //
//         "    display: flex;\r\n" + //
//         "    justify-content: space-between;\r\n" + //
//         "\r\n" + //
//         "    color: #fff;\r\n" + //
//         "    padding: 0 30px;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         ".invoice_banner .company_details {\r\n" + //
//         "    /* border: solid 1px red; */\r\n" + //
//         "    /* display: flex;\r\n" + //
//         "    flex-flow: column nowrap;\r\n" + //
//         "    justify-content: center;\r\n" + //
//         "    align-items: flex-start; */\r\n" + //
//         "    display: flex;\r\n" + //
//         "margin-top: -25px;\\r\\n" + //
                      
//         "flex-flow: column nowrap;\r\n" + //
//         "justify-content: center;\r\n" + //
//         "align-items: flex-start;\r\n" + //
//         "flex-direction: row;\r\n" + //
//         "flex-wrap: wrap;\r\n" + //
//         "align-content: space-around;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         ".invoice_banner .company_details svg {\r\n" + //
//         "    height: 38px;\r\n" + //
//         "    width: 25px;\r\n" + //
//         "    display: block\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         ".invoice_banner .company_details h2 {\r\n" + //
//         "    font-family: 'Protest Guerrilla', sans-serif;\r\n" + //
//         "    font-size: 30px;\r\n" + //
//         "    font-weight: 100;\r\n" + //
//         "    margin: 0;\r\n" + //
//         "    /* width: 75%; */\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         ".invoice_banner h2.document_title {\r\n" + //
//         "    text-transform: uppercase;\r\n" + //
//         "    font-family: \"Poppins\";\r\n" + //
//         "    height: fit-content;\r\n" + //
//         "    align-self: center;\r\n" + //
//         "    font-size: 40px;\r\n" + //
//         "    width: 26%;\r\n" + //
//         "    /* border: solid 1px red; */\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         ".address_info {\r\n" + //
//         "    margin: 30px;\r\n" + //
//         "    display: flex;\r\n" + //
//         "    justify-content: space-between;\r\n" + //
//         "    font-family: \"Poppins\";\r\n" + //
//         "    /* border-bottom: solid 3px gray; */\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         ".invoice_from,        .invoice_to {\r\n" + //
//         "    /* width: 26%; */\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         ".billing_container {\r\n" + //
//         "    padding: 0 30px;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         "#billing_info {\r\n" + //
//         "    width: -webkit-fill-available;\r\n" + //
//         "    text-align: left;\r\n" + //
//         "    border-collapse: collapse;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         "#billing_info thead tr {\r\n" + //
//         "    background-color: orange;\r\n" + //
//         "    height: 50px;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         "#billing_info th,\r\n" + //
//         "td {\r\n" + //
//         "    padding: 14px 16px;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         "#billing_info tbody tr:nth-of-type(even) {\r\n" + //
//         "    background-color: #e8e8d0;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         "#billing_info tbody tr:nth-last-child(1) {\r\n" + //
//         "    border-bottom: solid 3px gray;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         "#payment_info_container {\r\n" + //
//         "    display: flex;\r\n" + //
//         "    justify-content: flex-end;\r\n" + //
//         "    padding: 30px;\r\n" + //
//         "}\r\n" + //
//         "\r\n" + //
//         "#payment_info p {\r\n" + //
//         "    margin: 0;\r\n" + //
//         "    margin-bottom: 8px;\r\n" + //
//         "}\r\n" + //
//         "#billing_total{\r\n" + //
//         "    border-collapse: collapse;\r\n" + //
//         "}\r\n" + //
//         "#billing_total th{\r\n" + //
//         "    text-align: right;\r\n" + //
//         "}\r\n" + //
//         "#billing_total tbody tr:nth-child(1) th{\r\n" + //
//         "    color: orange;\r\n" + //
//         "}\r\n" + //
//         "#billing_total tr:nth-last-child(1){\r\n" + //
//         "    background-color: orange;\r\n" + //
//         "    font-size: 18px;\r\n" + //
//         "}</style></head><body>");
builder.append("<div class='invoice_modal'>");
builder.append("<div class='invoice_banner'>"
            +"<div class='company_details'>   "             
            +"   <svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='currentColor' class='bi bi-amd'"
            +"      viewBox='0 0 16 16'>"
            +"      <path d='m.334 0 4.358 4.359h7.15v7.15l4.358 4.358V0zM.2 9.72l4.487-4.488v6.281h6.28L6.48 16H.2z' />"
            +"  </svg>"
            +"  <h2>    My Store Pvt.Ltd</h2>"
            +" </div>"
            +"<h2 class='document_title'>Invoice</h2>"
            +"</div>");
builder.append("<div class='address_info'>\r\n" +//
        "            <div class='invoice_from'>\r\n" + //
        "                <strong> Customer : </strong><br/>\r\n" + //
        "                <span> <b>Name : </b> ").append(name).append("\r\n").append( //
        "                    <br/> Mobile : ").append(mobile).append("                   \r\n").append( //
        "                </span>\r\n").append( //
        "            </div>\r\n").append( //
        "            <div class='invoice_to'>\r\n").append( //
        "                <strong>Invoice No : </strong>").append(bill.getBill_pid()).append("<br/>\r\n").append( //
        "                <strong>Invoice Date : </strong>").append(bill.getDate()).append(" <br/>\r\n").append( //
        "                <strong>Payment Mode : </strong>").append(bill.getPayment_type()).append(" <br/>\r\n").append( //
        "                \r\n").append( //
        "            </div>\r\n").append( //
        "        </div>");

        builder.append("<div class='billing_container'><table id='billing_info'>                                    <thead>                                        <tr>                                            <th>#</th>                                            <th>Product Id</th>                                            <th>MRP</th>                                            <th>Quantity</th>                                            <th>Discount Amount</th>                                            <th>Total Amount</th>                                            <th>Net Amount</th>                                        </tr>                                    </thead>                                    <tbody>");
                                   
        int index=1;
        for (BillDetails bd : billDetails) {

            

            builder.append("  <tr>" + "  <td>").append(index).append("</td>").append("  <td>").append(bd.getProduct_fid().getProduct_pid()).append("</td>").append("  <td>").append(bd.getProduct_fid().getMrp()).append("</td>").append("  <td>").append(bd.getQuantity()).append("</td>").append("  <td>").append(bd.getDiscount_amount()).append("</td>").append("  <td>").append(bd.getTotal_amount()).append("</td>").append("  <td>").append(bd.getNet_amount()).append("</td>").append(" </tr>");
           
        index++;
        }
        builder.append("</tbody>  </table></div>");



        builder.append("<div id='payment_info_container'>\r\n" + //
                "<div id='billing_total_container'>\r\n" + //
                "                <table id='billing_total'>\r\n" + //
                "                    <tbody>\r\n" + //
                "                        <tr>\r\n" + //
                "                            <th>Total Amount : </th>\r\n" + //
                "                            <td>").append(bill.getAmount()).append("</td>\r\n").append( //
                "                        </tr>\r\n").append( //
                "                        <tr>\r\n").append( //
                "                            <th>Discount Amount : </th>\r\n").append( //
                "                            <td>").append(bill.getDiscount_amount()).append("</td>\r\n").append( //
                "                        </tr>\r\n").append( //
                "                        \r\n").append( //
                "                        <tr>\r\n").append( //
                "                            <th>Net Amount : </th>\r\n").append( //
                "                            <td>").append(bill.getNet_amount()).append("</td>\r\n").append( //
                "                        </tr>\r\n").append( //
                "                    </tbody>\r\n").append( //
                "                </table>\r\n").append( //
                "            </div>\r\n").append( //
                "</div>");
                        builder.append("<div class='row text-center contact-info'>\r\n" + //
                                "            <p>This is computer generated invoice</p>\r\n" + //
                                "            <div class='col-lg-12 col-md-12 col-sm-12' style='text-align: center;'>\r\n" + //
                                "                <hr/>\r\n" + //
                                "                <span style='padding: 10px 12px;'>\r\n" + //
                                "                    <strong>Email : </strong> MyStore@retail.com\r\n" + //
                                "                </span>\r\n" + //
                                "                <span style='padding: 10px 12px;'>\r\n" + //
                                "                    <strong>Call : </strong> +1-623-777-9044\r\n" + //
                                "                </span>\r\n" + //
                                "                <span style='padding: 10px 12px;'>\r\n" + //
                                "                    <strong>Fax : </strong> +012340-908- 890\r\n" + //
                                "                </span>\r\n" + //
                                "                <hr/>\r\n" + //
                                "            </div>\r\n" + //
                                "        </div>\r\n" + //
                                "    </div>\r\n" //
                                // "</body>\r\n" + //
                                // "\r\n" + //
                                // "</html>"
                                );
                             
                                
                            
                                      
                                    
                            } catch (Exception e) {
                                e.printStackTrace();
                              }


    return builder.toString();
}
@Override
    public String invoiceStaticHtml()
    {
        return 
                        "<html><head><title>Print</title>\r\n" + //
                        "    <style>\r\n" + //                        
                        "\r\n" + //
                        "    /* font-family: 'Protest Guerrilla', sans-serif; */\r\n" + //
                        "    body {\r\n" + //
                        "        background: beige;\r\n" + //
                        "        font-family: 'Poppins';\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    .invoice_modal {\r\n" + //
                        "        width: 900px;\r\n" + //
                        "        margin-top: 50px;\r\n" + //
                        "        margin: 0 auto;\r\n" + //
                        "        background: beige;\r\n" + //
                        "        font-family: 'Poppins';\r\n" + //
                        "        border: 2px solid;\r\n" + //
                        "padding: 7px;\r\n" + //
                        "border: 1px solid;\r\n" + //
                        "padding:7px ;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    .invoice_banner {\r\n" + //
                        "        /* min-height: 200px; */\r\n" + //
                        "        height: 50px;\r\n" + //
                        "        background-color: #02090a;\r\n" + //
                        "        display: none;\r\n" + //
                        "        justify-content: space-between;\r\n" + //
                        "\r\n" + //
                        "        color: #fff;\r\n" + //
                        "        padding: 0 30px;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    .invoice_banner .company_details {\r\n" + //
                        "        /* border: solid 1px red; */\r\n" + //
                        "        /* display: flex;\r\n" + //
                        "        flex-flow: column nowrap;\r\n" + //
                        "        justify-content: center;\r\n" + //
                        "        align-items: flex-start; */\r\n" + //
                        "        display: flex;\r\n" + //
                        "flex-flow: column nowrap;\r\n" + //
                        "justify-content: center;\r\n" + //
                        "align-items: flex-start;\r\n" + //
                        "flex-direction: row;\r\n" + //
                        "flex-wrap: wrap;\r\n" + //
                        "align-content: space-around;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    .invoice_banner .company_details svg {\r\n" + //
                        "        height: 38px;\r\n" + //
                        "        width: 25px;\r\n" + //
                        "        display: block;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    .invoice_banner .company_details h2 {\r\n" + //
                        "        /**font-family: 'Protest Guerrilla', sans-serif;*/\r\n" + //
                        "        font-size: 30px;\r\n" + //
                        "        font-weight: 100;\r\n" + //
                        "        margin: 0;\r\n" + //
                        "        /* width: 75%; */\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    .invoice_banner h2.document_title {\r\n" + //
                        "        text-transform: uppercase;\r\n" + //
                        "        font-family: 'Poppins';\r\n" + //
                        "        height: fit-content;\r\n" + //
                        "        align-self: center;\r\n" + //
                        "        font-size: 40px;\r\n" + //
                        "        width: 26%;\r\n" + //
                        "        /* border: solid 1px red; */\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    .address_info {\r\n" + //
                        "        margin: 30px;\r\n" + //
                        "        display: flex;\r\n" + //
                        "        justify-content: space-between;\r\n" + //
                        "        font-family: 'Poppins';\r\n" + //
                        "        /* border-bottom: solid 3px gray; */\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    .invoice_from,\r\n" + //
                        "    .invoice_to {\r\n" + //
                        "        /* width: 26%; */\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    .billing_container {\r\n" + //
                        "        padding: 0 30px;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    #billing_info {\r\n" + //
                        "        width: -webkit-fill-available;\r\n" + //
                        "        text-align: left;\r\n" + //
                        "        border-collapse: collapse;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    #billing_info thead tr {\r\n" + //
                        "        background-color: orange;\r\n" + //
                        "        height: 50px;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    #billing_info th,\r\n" + //
                        "    td {\r\n" + //
                        "        padding: 14px 16px;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    #billing_info tbody tr:nth-of-type(even) {\r\n" + //
                        "        background-color: #e8e8d0;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    #billing_info tbody tr:nth-last-child(1) {\r\n" + //
                        "        border-bottom: solid 3px gray;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    #payment_info_container {\r\n" + //
                        "        display: flex;\r\n" + //
                        "        justify-content: flex-end;\r\n" + //
                        "        padding: 30px;\r\n" + //
                        "    }\r\n" + //
                        "\r\n" + //
                        "    #payment_info p {\r\n" + //
                        "        margin: 0;\r\n" + //
                        "        margin-bottom: 8px;\r\n" + //
                        "    }\r\n" + //
                        "    #billing_total{\r\n" + //
                        "        border-collapse: collapse;\r\n" + //
                        "    }\r\n" + //
                        "    #billing_total th{\r\n" + //
                        "        text-align: right;\r\n" + //
                        "    }\r\n" + //
                        "    #billing_total tbody tr:nth-child(1) th{\r\n" + //
                        "        color: orange;\r\n" + //
                        "    }\r\n" + //
                        "    #billing_total tr:nth-last-child(1){\r\n" + //
                        "        background-color: orange;\r\n" + //
                        "        font-size: 18px;\r\n" + //
                        "    }\r\n" + //
                        "</style></head><body><div>";
    }
    @Override
    public String invoicemailbody(Bill bill)
    {
        return "Dear "+bill.getCustomer_fid().getName()+",\r\n" + //
                        "\r\n" + //
                        "I hope this email finds you well. We are writing to provide you with the invoice for your recent order "+bill.getBill_pid()+" placed with My Store Pvt.Ltd.\r\n" + //
                        "\r\n" + //
                        "Attached to this email, you will find the invoice detailing the items you have purchased, along with the total amount due. Please review the invoice and let us know if you have any questions or concerns regarding the billing.\r\n" + //
                        "\r\n" + //
                        "If everything looks accurate, we kindly request that you process the payment at your earliest convenience. You can find the payment details on the invoice.\r\n" + //
                        "\r\n" + //
                        "Thank you for choosing My Store Pvt.Ltd. We appreciate your business and look forward to serving you again in the future.\r\n" + //
                        "\r\n" + //
                        
                        "Best regards,\r\n" + //
                        "\r\n" + //
                        "My Store Pvt.Ltd\r\n"
                       ;
    }
    
}
