var obj_product = {};
$(document).ready(() => {
  // $('input[data-field="quantity"]').on("change", function () {

  //   alert(1);
  // });





  $("#btn_generateInvoice").click(() => {

    $(".customloader").show();
    if (!validPanel("pnl_invoiceDetails")) {
      $("#customloader").hide();
      return;
    }

    debugger;
    var rowcount = $("#tbl_product tbody tr");
    if (rowcount == null || rowcount.length <= 0) {
      showToasty(
        "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
        "bg-danger",
        "Please Add Product For Invoice...",
        "Error"
      );
      return;
    }

    if ($("#html_paymenttype").val() == "Cash") {

      generateInvoice()

    }
    else {
      var a = [];
    Object.keys(obj_product).forEach(function (index) {
      a.push(obj_product[index]);
    });
    var objBill = {};
    objBill["payment_type"] = $("#html_paymenttype").val();

    objBill["_customer_fid"] = $("#html_customer").val()
    if ($("#html_bill_Pid").val() != '')
      objBill["bill_pid"] = $("#html_bill_Pid").val()

    var obj = {
      product: a,
      bill: objBill
    }
      $.ajax(
        {
          
          url: "/Create-Payment-Order",
          type: "POST",
          contentType: "application/json",
          data: JSON.stringify(obj), 
          success: function (response) {
            displayLoader(false)
            response=JSON.parse(response)
            if (response.status == "created") {
              //open payment form
              let options = {
                key: "rzp_test_2A5WF7VAuSATXf",
                amount: response.amount,
                currency: "INR",
                name: "Retail Store Management System",
                description: "Bill Payment",
                image:
                ` <svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='currentColor' class='bi bi-amd'"
                viewBox='0 0 16 16'>               <path d='m.334 0 4.358 4.359h7.15v7.15l4.358 4.358V0zM.2 9.72l4.487-4.488v6.281h6.28L6.48 16H.2z' />
             </svg>`,
                  // "https://yt3.ggpht.com/-4BGUu55s_ko/AAAAAAAAAAI/AAAAAAAAAAA/3Cfl_C4o8Uo/s108-c-k-c0x00ffffff-no-rj-mo/photo.jpg",
                order_id: response.id,
                handler: function (response) {
               
                  swal("congrates !! Payment successful !!","Please Wait for Invoice Printing", "success");
                  setTimeout(function() {
                    swal.close();
                }, 2000);
                generateInvoice()

                },
                prefill: {
                  name: "",
                  email: "",
                  contact: "",
                },

                notes: {
                  address: "LearnCodeWith Durgesh ",
                },
                theme: {
                  color: "#3399cc",
                },
              };

              let rzp = new Razorpay(options);

              rzp.on("payment.failed! Please Try Again...", function (response) {
                // console.log(response.error.code);
                // console.log(response.error.description);
                // console.log(response.error.source);
                // console.log(response.error.step);
                // console.log(response.error.reason);
                // console.log(response.error.metadata.order_id);
                // console.log(response.error.metadata.payment_id);
                //alert("Oops payment failed !!");
                  
              });

              rzp.open();
            }
          },
          error: function (error) {
            displayLoader(false)
            //invoked when error
            console.log(error);
            alert("something went wrong !!");
          },
        }
      );


    }




  });


  function generateInvoice() {
    var a = [];
    Object.keys(obj_product).forEach(function (index) {
      a.push(obj_product[index]);
    });
    var objBill = {};
    objBill["payment_type"] = $("#html_paymenttype").val();

    objBill["_customer_fid"] = $("#html_customer").val()
    if ($("#html_bill_Pid").val() != '')
      objBill["bill_pid"] = $("#html_bill_Pid").val()

    var obj = {
      product: a,
      bill: objBill
    }


    $.ajax({
      type: "POST",
      contentType: "application/json",
      url: "/generateinvoice",
      //dataType: 'json',
      data: JSON.stringify(obj),
      success: function (data, textStatus, xhr) {

        debugger;
       

        showToasty(
          "<i class='fa-solid fa-square-check fa-shake fa-xl text-white'>&nbsp;&nbsp</i>",
          "bg-success",
          "Invoice Generated Successfully...",
          "Success"
        );
        $("#Invoice").html(data);
        $('.in').hide();
        $('#Invoice').prepend(` <button class="noselect btn_add ml in" style="margin-left: 80%;
        margin-bottom: 11px;
        background: #091cff;" 
        
        id="printInvoice">
        <span class="text">Print</span><span class="icon">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path d="M448 192V77.3c0-8.5-3.4-16.6-9.4-22.6L393.4 9.4c-6-6-14.1-9.4-22.6-9.4H96C78.3 0 64 14.3 64 32v160c-35.4 0-64 28.7-64 64v112c0 8.8 7.2 16 16 16h48v96c0 17.7 14.3 32 32 32h320c17.7 0 32-14.3 32-32v-96h48c8.8 0 16-7.2 16-16V256c0-35.4-28.7-64-64-64zm-64 256H128v-96h256v96zm0-224H128V64h192v48c0 8.8 7.2 16 16 16h48v96zm48 72c-13.3 0-24-10.8-24-24 0-13.3 10.8-24 24-24s24 10.7 24 24c0 13.3-10.8 24-24 24z"/></svg>
        </span>
      </button>`);
      document.getElementById("printInvoice").addEventListener("click", function() {
        $("#printInvoice").hide()
    var printWindow = window.open('', '_blank');

    printWindow.document.write(`<html><head><title>Print</title>
    <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Protest+Guerrilla&display=swap');

    /* font-family: 'Protest Guerrilla', sans-serif; */
    body {
        background: beige;
        font-family: 'Poppins';
    }

    .invoice_modal {
        width: 900px;
        margin-top: 50px;
        margin: 0 auto;
        background: beige;
        font-family: 'Poppins';
        border: 2px solid;
padding: 7px;
border: 1px solid;
padding:7px ;
    }

    .invoice_banner {
        /* min-height: 200px; */
        height: 50px;
        background-color: #02090a;
        display: flex;
        justify-content: space-between;

        color: #fff;
        padding: 0 30px;
    }

    .invoice_banner .company_details {
        /* border: solid 1px red; */
        /* display: flex;
        flex-flow: column nowrap;
        justify-content: center;
        align-items: flex-start; */
        display: flex;
flex-flow: column nowrap;
justify-content: center;
align-items: flex-start;
flex-direction: row;
flex-wrap: wrap;
align-content: space-around;
    }

    .invoice_banner .company_details svg {
        height: 38px;
        width: 25px;
        display: block
    }

    .invoice_banner .company_details h2 {
        font-family: 'Protest Guerrilla', sans-serif;
        font-size: 30px;
        font-weight: 100;
        margin: 0;
        /* width: 75%; */
    }

    .invoice_banner h2.document_title {
        text-transform: uppercase;
        font-family: 'Poppins';
        height: fit-content;
        align-self: center;
        font-size: 40px;
        width: 26%;
        /* border: solid 1px red; */
    }

    .address_info {
        margin: 30px;
        display: flex;
        justify-content: space-between;
        font-family: 'Poppins';
        /* border-bottom: solid 3px gray; */
    }

    .invoice_from,
    .invoice_to {
        /* width: 26%; */
    }

    .billing_container {
        padding: 0 30px;
    }

    #billing_info {
        width: -webkit-fill-available;
        text-align: left;
        border-collapse: collapse;
    }

    #billing_info thead tr {
        background-color: orange;
        height: 50px;
    }

    #billing_info th,
    td {
        padding: 14px 16px;
    }

    #billing_info tbody tr:nth-of-type(even) {
        background-color: #e8e8d0;
    }

    #billing_info tbody tr:nth-last-child(1) {
        border-bottom: solid 3px gray;
    }

    #payment_info_container {
        display: flex;
        justify-content: flex-end;
        padding: 30px;
    }

    #payment_info p {
        margin: 0;
        margin-bottom: 8px;
    }
    #billing_total{
        border-collapse: collapse;
    }
    #billing_total th{
        text-align: right;
    }
    #billing_total tbody tr:nth-child(1) th{
        color: orange;
    }
    #billing_total tr:nth-last-child(1){
        background-color: orange;
        font-size: 18px;
    }
</style>`);
    printWindow.document.write(`</head><body><div>`);
    printWindow.document.write($("#Invoice").html());
    printWindow.document.write('</div></body></html>');


    
    
    printWindow.document.close();
        printWindow.print();
      });
        // setTimeout(function () {
        //   location.reload();
        // }, 2000);
      },
      error: function (xhr, textStatus, errorThrown) {
        showToasty(
          "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
          "bg-danger",
          "Something Went Wrong! Please Try Again...",
          "Error"
        );
      },
    });

  }
  function printinvoice()
  {
    
    var printWindow = window.open('', '_blank');

    printWindow.document.write(`<html><head><title>Print</title>
    <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Protest+Guerrilla&display=swap');

    /* font-family: 'Protest Guerrilla', sans-serif; */
    body {
        background: beige;
        font-family: 'Poppins';
    }

    .invoice_modal {
        width: 900px;
        margin-top: 50px;
        margin: 0 auto;
        background: beige;
        font-family: 'Poppins';
        border: 2px solid;
padding: 7px;
border: 1px solid;
padding:7px ;
    }

    .invoice_banner {
        /* min-height: 200px; */
        height: 50px;
        background-color: #02090a;
        display: flex;
        justify-content: space-between;

        color: #fff;
        padding: 0 30px;
    }

    .invoice_banner .company_details {
        /* border: solid 1px red; */
        /* display: flex;
        flex-flow: column nowrap;
        justify-content: center;
        align-items: flex-start; */
        display: flex;
flex-flow: column nowrap;
justify-content: center;
align-items: flex-start;
flex-direction: row;
flex-wrap: wrap;
align-content: space-around;
    }

    .invoice_banner .company_details svg {
        height: 38px;
        width: 25px;
        display: block
    }

    .invoice_banner .company_details h2 {
        font-family: 'Protest Guerrilla', sans-serif;
        font-size: 30px;
        font-weight: 100;
        margin: 0;
        /* width: 75%; */
    }

    .invoice_banner h2.document_title {
        text-transform: uppercase;
        font-family: 'Poppins';
        height: fit-content;
        align-self: center;
        font-size: 40px;
        width: 26%;
        /* border: solid 1px red; */
    }

    .address_info {
        margin: 30px;
        display: flex;
        justify-content: space-between;
        font-family: 'Poppins';
        /* border-bottom: solid 3px gray; */
    }

    .invoice_from,
    .invoice_to {
        /* width: 26%; */
    }

    .billing_container {
        padding: 0 30px;
    }

    #billing_info {
        width: -webkit-fill-available;
        text-align: left;
        border-collapse: collapse;
    }

    #billing_info thead tr {
        background-color: orange;
        height: 50px;
    }

    #billing_info th,
    td {
        padding: 14px 16px;
    }

    #billing_info tbody tr:nth-of-type(even) {
        background-color: #e8e8d0;
    }

    #billing_info tbody tr:nth-last-child(1) {
        border-bottom: solid 3px gray;
    }

    #payment_info_container {
        display: flex;
        justify-content: flex-end;
        padding: 30px;
    }

    #payment_info p {
        margin: 0;
        margin-bottom: 8px;
    }
    #billing_total{
        border-collapse: collapse;
    }
    #billing_total th{
        text-align: right;
    }
    #billing_total tbody tr:nth-child(1) th{
        color: orange;
    }
    #billing_total tr:nth-last-child(1){
        background-color: orange;
        font-size: 18px;
    }
</style>`);
    printWindow.document.write(`</head><body><div>`);
    printWindow.document.write($("#Invoice").html());
    printWindow.document.write('</div></body></html>');


    printWindow.document.open();
    printWindow.document.write(data);
    printWindow.document.close();
        printWindow.print();
  }
















  $("#printInvoice").click(() =>{
  var printWindow = window.open('', '_blank');

    printWindow.document.write(`<html><head><title>Print</title>
    <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Protest+Guerrilla&display=swap');

    /* font-family: 'Protest Guerrilla', sans-serif; */
    body {
        background: beige;
        font-family: 'Poppins';
    }

    .invoice_modal {
        width: 900px;
        margin-top: 50px;
        margin: 0 auto;
        background: beige;
        font-family: 'Poppins';
        border: 2px solid;
padding: 7px;
border: 1px solid;
padding:7px ;
    }

    .invoice_banner {
        /* min-height: 200px; */
        height: 50px;
        background-color: #02090a;
        display: flex;
        justify-content: space-between;

        color: #fff;
        padding: 0 30px;
    }

    .invoice_banner .company_details {
        /* border: solid 1px red; */
        /* display: flex;
        flex-flow: column nowrap;
        justify-content: center;
        align-items: flex-start; */
        display: flex;
flex-flow: column nowrap;
justify-content: center;
align-items: flex-start;
flex-direction: row;
flex-wrap: wrap;
align-content: space-around;
    }

    .invoice_banner .company_details svg {
        height: 38px;
        width: 25px;
        display: block
    }

    .invoice_banner .company_details h2 {
        font-family: 'Protest Guerrilla', sans-serif;
        font-size: 30px;
        font-weight: 100;
        margin: 0;
        /* width: 75%; */
    }

    .invoice_banner h2.document_title {
        text-transform: uppercase;
        font-family: 'Poppins';
        height: fit-content;
        align-self: center;
        font-size: 40px;
        width: 26%;
        /* border: solid 1px red; */
    }

    .address_info {
        margin: 30px;
        display: flex;
        justify-content: space-between;
        font-family: 'Poppins';
        /* border-bottom: solid 3px gray; */
    }

    .invoice_from,
    .invoice_to {
        /* width: 26%; */
    }

    .billing_container {
        padding: 0 30px;
    }

    #billing_info {
        width: -webkit-fill-available;
        text-align: left;
        border-collapse: collapse;
    }

    #billing_info thead tr {
        background-color: orange;
        height: 50px;
    }

    #billing_info th,
    td {
        padding: 14px 16px;
    }

    #billing_info tbody tr:nth-of-type(even) {
        background-color: #e8e8d0;
    }

    #billing_info tbody tr:nth-last-child(1) {
        border-bottom: solid 3px gray;
    }

    #payment_info_container {
        display: flex;
        justify-content: flex-end;
        padding: 30px;
    }

    #payment_info p {
        margin: 0;
        margin-bottom: 8px;
    }
    #billing_total{
        border-collapse: collapse;
    }
    #billing_total th{
        text-align: right;
    }
    #billing_total tbody tr:nth-child(1) th{
        color: orange;
    }
    #billing_total tr:nth-last-child(1){
        background-color: orange;
        font-size: 18px;
    }
</style>`);
    printWindow.document.write(`</head><body><div>`);
    printWindow.document.write($("#Invoice").html());
    printWindow.document.write('</div></body></html>');


    printWindow.document.open();
    printWindow.document.write(data);
    printWindow.document.close();
        printWindow.print();
  });


  $("#btn_addProduct").click(() => {
    debugger;
    $("#toast").css("display", "none");
    //if (validPanel("pnl_invoiceDetails")) 
    if ($("#html_productID").val() != null && $("#html_productID").val() != "") {
      product = {};
      if (obj_product[$("#html_productID").val()] != null) {
        product = obj_product[$("#html_productID").val()];
      }
      var data = {
        id: $("#html_productID").val(),
        qty: $("#html_quantity").val(),
        exist_pid:
          product["product_pid"] == null
            ? ""
            : product["product_pid"].toString(),
        exist_qty:
          product["quantity"] == null ? "" : product["quantity"].toString(),
        billDetail_pid: product["billDetails_pid"] == null ? "" : product["billDetails_pid"].toString()
      };
      $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/searchProduct",
        data: JSON.stringify(data),
        success: function (res) {
          console.log(obj_product);
          var p = res;
          obj_product[p["product_pid"]] = p;
          if (Object.keys(obj_product).length > 0) {
            buildTable(obj_product);
          }

          showToasty(
            "<i class='fa-solid fa-square-check fa-shake fa-xl text-white'>&nbsp;&nbsp</i>",
            "bg-success",
            "Product added...",
            "Success"
          );
        },
        error: (res) => {
          showToasty(
            "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
            "bg-danger",
            (res["responseText"] = "" ? "Error..." : res["responseText"]),
            "Error"
          );

          console.log(res);
        },
      });
    }
  });
});
function dicount_change(elem) {
  debugger;
  var percentage = $(elem).val();
  var id = $(elem).closest("tr").find("[type=hidden][data-field=id]");


  if (percentage < 0) {
    $(elem).val(0)
    buildTable(obj_product);
    return;

  } else {

    if (obj_product[id.val()] != null) {

      var obj = obj_product[id.val()];
      if (percentage >= 100) {
        $(elem).val(obj["discountper"])
        percentage = obj["discountper"]
        buildTable(obj_product);
        showToasty(
          "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
          "bg-danger",
          "Cannot apply Full Discount...",
          "Error"
        );
        return;

      }





      var mrp = obj["total_amount"] - (obj["total_amount"] * percentage) / 100;
      obj["netamount"] = mrp;
      obj["discountper"] = percentage;
      obj["discountamt"] = (obj["total_amount"] * percentage) / 100
      buildTable(obj_product);
    }
    else { }






  }



}
function quantity_change(elem) {
  debugger;
  var qty = $(elem).val();
  if (qty <= 0) {
    $(elem).val(1)
    buildTable(obj_product);
    return;
  }

  var tr = $(elem).closest("tr").find("[type=hidden][data-field=id]");
  //  obj_product[tr.val()].qty=qty;
  //  obj_product[tr.val()].total_amount=qty*obj_product[tr.val()].mrp;
  // $(this).closest("tr").find("[type=number][data-field=total_amount]").val(qty*obj_product[tr.val()].mrp)

  var data = {
    id: tr.val(),
    qty: qty,
    billDetail_pid: obj_product[tr.val()]["billDetails_fid"] == null ? "" : obj_product[tr.val()]["billDetails_fid"].toString()

  };
  $.ajax({
    type: "POST",
    contentType: "application/json",
    url: "/searchProduct",
    data: JSON.stringify(data),
    success: function (res) {
      console.log(obj_product);
      var p = res;
      obj_product[p["product_pid"]] = p;
      buildTable(obj_product);
    },
    error: (res) => {
      buildTable(obj_product);
      showToasty(
        "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
        "bg-danger",
        (res["responseText"] = "" ? "Error..." : res["responseText"]),
        "Error"
      );

      console.log(res);
    },
  });
}
function buildTable(obj_product) {
  debugger;
  $("#tbl_product tbody").html("");
  var total_qty = 0;
  var total_disamount = 0;
  var total_amount = 0;
  var net_amount = 0;
  Object.keys(obj_product).forEach((key, i) => {
    var element = obj_product[key];

    total_qty += element["quantity"];
    total_disamount += element["discountamt"];
    total_amount += element["total_amount"];
    net_amount += element["netamount"];

    $("#tbl_product tbody").append(` <tr id="addr0" data-id="0">


    <td data-name="mail" style='display:none'>
    <div class="form-floating mb-3 ">
      <input type="number" readonly class="form-control" value=${element["billDetails_fid"]} id="html_billDetails_fid-${i}" data-field="billDetails_fid" />
      <label for="floatingInput mr-5" class="ml-15 md_lbl">billDetails_pid</label>
      <span class="_error"></span>
    </div>
  </td>

    <td data-name="mail" style='display:flex'>
    <div>
    <label style='    margin-left: -7px;
    padding: 4px;
    border-bottom: 1px solid;'><b>${i + 1}</b></label>
    </div>
      <div class="form-floating mb-3 ">
      
        <input type="text" class="form-control" readonly value=${element["product_pid"]} id="html_ProductID-${i}" data-field="" />
        <input type="hidden" class="form-control" value=${element["product_pid"]} id="html_ProductID-${i}" data-field="id" />
        <label for="floatingInput mr-5"  class="ml-15 md_lbl">Product ID</label>
        <span class="_error"></span>
      </div>
    </td>
    <!--
    <td data-name="mail">
      <div class="form-floating mb-3 ">
        <input type="text" readonly class="form-control" value=${element["style"]} id="html_style-${i}" data-field="name" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Product Style</label>
        <span class="_error"></span>
      </div>
    </td>-->
    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" readonly class="form-control" value=${element["mrp"]} id="html_size-${i}" data-field="size" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">MRP</label>
        <span class="_error"></span>
      </div>
    </td>


    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" class="form-control" min="0" onchange="quantity_change(this)" id="html_qty-${i}"  value=${element["quantity"]} data-field="quantity" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Quantity</label>
        <span class="_error"></span>
      </div>
    </td>
    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" class="form-control" min="0" onchange="dicount_change(this)" value=${element["discountper"]} id="html_MRP-${i}" data-field="mrp" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Discount(%)</label>
        <span class="_error"></span>
      </div>
    </td>
    <td data-name="mail">
    <div class="form-floating mb-3 mandatory">
      <input type="number" class="form-control" readonly id="html_MRP-${i}" value=${element["discountamt"]} data-field="purchase_rate" />
      <label for="floatingInput mr-5" class="ml-15 md_lbl">Discount(₹)</label>
      <span class="_error"></span>
    </div>
  </td>
    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" class="form-control" readonly id="html_net_amount-${i}" value=${element["netamount"]} data-field="net_amount" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Net Amount(₹)</label>
        <span class="_error"></span>
      </div>
    </td>
    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" class="form-control" readonly id="html_Total-${i}" value=${element["total_amount"]} data-field="total_amount" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Total</label>
        <span class="_error"></span>
      </div>
    </td>    
    
    <td data-name="del">
    <button onClick="removebtn(this)"
      name="del0"
      class="btn btn-danger glyphicon glyphicon-remove row-remove btn-row-remove"
    >
      <span aria-hidden="true">×</span>
    </button>
  </td>

  </tr>`);
  });
  $("#tbl_product tfoot tr td:nth-child(2) label").text(total_qty);
  $("#tbl_product tfoot tr td:nth-child(4) label").text(total_disamount);
  $("#tbl_product tfoot tr td:nth-child(5) label").text(net_amount);
  $("#tbl_product tfoot tr td:nth-child(6) label").text(total_amount);
}

// document.getElementsByClassName("btn-row-remove").click(removebtn)

const removebtn = (elem) => {
  var result = confirm("Do you want to remove this product?");
  if (result) {
    var closestid = $(elem).closest("tr").find("[type=hidden][data-field=id]").val()
    //$(this).closest("tr").find("[data-field='id']").val()
    delete obj_product[closestid]
    buildTable(obj_product);
  }


}