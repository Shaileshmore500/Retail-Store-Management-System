var obj_product = {};
$(document).ready(() => {
  // $('input[data-field="quantity"]').on("change", function () {
  //   debugger;
  //   alert(1);
  // });

  $("#btn_generateInvoice").click(() => {
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

  
    

    $.ajax({
      type: "POST",
      contentType: "application/json",
      url: "/generateinvoice",
      data: JSON.stringify({"list":obj_product}),
      success: function (res) {

        console.log("suc");
        console.log(res);
      },
      error: (res) => {
        console.log("err");
        console.log(res);
      },
    });
  });

  $("#btn_addProduct").click(() => {
    debugger;
    $("#toast").css("display", "none");
    if (validPanel("pnl_invoiceDetails")) {
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
function cal_totalamount() {}
function quantity_change(elem) {
  debugger;
  var qty = $(elem).val();
  var tr = $(elem).closest("tr").find("[type=hidden][data-field=id]");
  //  obj_product[tr.val()].qty=qty;
  //  obj_product[tr.val()].total_amount=qty*obj_product[tr.val()].mrp;
  // $(this).closest("tr").find("[type=number][data-field=total_amount]").val(qty*obj_product[tr.val()].mrp)

  var data = {
    id: tr.val(),
    qty: qty,
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
  $("#tbl_product tbody").html("");
  var total_qty = 0;
  var total_disamount = 0;
  var total_amount = 0;
  Object.keys(obj_product).forEach((key, i) => {
    var element = obj_product[key];

    total_qty += element["quantity"];
    total_disamount += 0;
    total_amount += element["total_amount"];

    $("#tbl_product tbody").append(` <tr id="addr0" data-id="0">
    <td data-name="mail">
    
      <div class="form-floating mb-3 ">
        <input type="text" class="form-control" value=${element["product_pid"]} id="html_ProductID-${i}" data-field="" />
        <input type="hidden" class="form-control" value=${element["product_pid"]} id="html_ProductID-${i}" data-field="id" />
        <label for="floatingInput mr-5"  class="ml-15 md_lbl">Product ID</label>
        <span class="_error"></span>
      </div>
    </td>
    <td data-name="mail">
      <div class="form-floating mb-3 ">
        <input type="text" class="form-control" value=${element["style"]} id="html_style-${i}" data-field="name" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Product Style</label>
        <span class="_error"></span>
      </div>
    </td>
    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" class="form-control" value=${element["mrp"]} id="html_size-${i}" data-field="size" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">MRP</label>
        <span class="_error"></span>
      </div>
    </td>


    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" class="form-control" onchange="quantity_change(this)" id="html_qty-${i}"  value=${element["quantity"]} data-field="quantity" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Quantity</label>
        <span class="_error"></span>
      </div>
    </td>
    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" class="form-control" value=${element["discountper"]} id="html_MRP-${i}" data-field="mrp" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Discount(%)</label>
        <span class="_error"></span>
      </div>
    </td>
    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" class="form-control" id="html_MRP-${i}" value=${element["discountamt"]} data-field="purchase_rate" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Discount(₹)</label>
        <span class="_error"></span>
      </div>
    </td>
    <td data-name="mail">
      <div class="form-floating mb-3 mandatory">
        <input type="number" class="form-control" id="html_MRP-${i}" value=${element["total_amount"]} data-field="total_amount" />
        <label for="floatingInput mr-5" class="ml-15 md_lbl">Total</label>
        <span class="_error"></span>
      </div>
    </td>                  
  </tr>`);
  });
  $("#tbl_product tfoot tr td:nth-child(2) label").text(total_qty);
  $("#tbl_product tfoot tr td:nth-child(4) label").text(total_disamount);
  $("#tbl_product tfoot tr td:nth-child(5) label").text(total_amount);
}
