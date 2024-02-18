var obj_product = {};
$(document).ready(() => {
  // $('input[data-field="quantity"]').on("change", function () {
  
  //   alert(1);
  // });





  $("#btn_generateInvoice").click(() => {
    
    $(".customloader").show();
    if(!validPanel("pnl_invoiceDetails"))
    {
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

    
    
    // var a=[];
    // for (let index = 0; index < Object.keys(obj_product).length; index++)
    // {
    // a.push(obj_product[index])  ;
    // }
    var a = [];
Object.keys(obj_product).forEach(function(index) {
    a.push(obj_product[index]);
});
var objBill={};
objBill["payment_type"]=$("#html_paymenttype").val();

objBill["_customer_fid"]=$("#html_customer").val()
if($("#html_bill_Pid").val()!='')
objBill["bill_pid"]=$("#html_bill_Pid").val()

    var obj={product:a,
      bill:objBill
    }
    

    $.ajax({
      type: "POST",
      contentType: "application/json",
      url: "/generateinvoice",
      //dataType: 'json',
      data: JSON.stringify(obj),
      success: function(data, textStatus, xhr) {

        showToasty(
          "<i class='fa-solid fa-square-check fa-shake fa-xl text-white'>&nbsp;&nbsp</i>",
          "bg-success",
          "Invoice Generated Successfully...",
          "Success"
        );
        setTimeout(function() {
          location.reload();
      }, 2000);
      },
      error: function(xhr, textStatus, errorThrown) {
        showToasty(
          "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
          "bg-danger",
          "Something Went Wrong! Please Try Again...",
          "Error"
        );
      },
    });
  });

  $("#btn_addProduct").click(() => {
    debugger;
    $("#toast").css("display", "none");
    //if (validPanel("pnl_invoiceDetails")) 
    if($("#html_productID").val()!=null && $("#html_productID").val() != "")
    {
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
          billDetail_pid:product["billDetails_pid"] == null ? "" : product["billDetails_pid"].toString()
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
  var percentage=$(elem).val();
  var id = $(elem).closest("tr").find("[type=hidden][data-field=id]");

  
   if(percentage<0)
  {
    $(elem).val(0)
    buildTable(obj_product);
    return;

  }else
  {

    if(obj_product[id.val()]!=null)
    {

      var obj=obj_product[id.val()];
      if(percentage>=100)
      {
        $(elem).val(obj["discountper"])
        percentage=obj["discountper"]
        buildTable(obj_product);
        showToasty(
          "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
          "bg-danger",
          "Cannot apply Full Discount..." ,
          "Error"
        );
        return;
       
      }




      
      var mrp=obj["total_amount"]-(obj["total_amount"]*percentage)/100;
      obj["netamount"]=mrp;
      obj["discountper"]=percentage;
      obj["discountamt"]=(obj["total_amount"]*percentage)/100
      buildTable(obj_product);
    }
    else{}


    



  }



}
function quantity_change(elem) {
  debugger;
  var qty = $(elem).val();
  if(qty<=0)
  {
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
    billDetail_pid:obj_product[tr.val()]["billDetails_fid"] == null ? "" : obj_product[tr.val()]["billDetails_fid"].toString()
      
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
  var net_amount=0;
  Object.keys(obj_product).forEach((key, i) => {
    var element = obj_product[key];

    total_qty += element["quantity"];
    total_disamount += element["discountamt"];
    total_amount += element["total_amount"];
    net_amount+=element["netamount"];

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
    border-bottom: 1px solid;'><b>${i+1 }</b></label>
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

const removebtn=(elem)=>{
  var result = confirm("Do you want to remove this product?");
if (result) {
  var closestid=$(elem).closest("tr").find("[type=hidden][data-field=id]").val()
  //$(this).closest("tr").find("[data-field='id']").val()
  delete obj_product[closestid]
  buildTable(obj_product);
}

  
}