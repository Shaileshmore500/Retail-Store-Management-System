
function showToasty(icon, bgclass, message, stat) {
  debugger;

  $("#toast div i").remove();
  $("#toast div strong").before(icon);
  $("#toast div strong").text(stat);
  $("#toast .toast-header").addClass(bgclass);
  $(".toast-body").text(message);
  $("#toast").css("opacity",1);
  // $("#toast").show(()=>{
  //   setTimeout(function(){
  //     //$("#toast").hide()
  //     //$("#toast").css("opacity",0)
  //   $("#toast").animate({ opacity: 0 }, 3000).hide()
  //     }  ,3000)
  // ;
  //   });
  $("#toast").show(()=>{
  var toastElement = $("#toast");

  // Hide the element with opacity animation and a delay of 3000 milliseconds (3 seconds)
  toastElement.animate({ opacity: 0 }, 5000, function(){
      // This callback function will be executed after the animation is complete
      // Use .stop() to clear any ongoing animations and prevent hide when hovered
      toastElement.stop(true, true).hide();
  });
  
  // Prevent the element from hiding when hovered
  toastElement.hover(function(){
      // Mouse enter event: clear the hide animation using .stop()
      $(this).stop().css('opacity', 1);;
  }, function(){
      // Mouse leave event: resume the hide animation
      $(this).animate({ opacity: 0 }, 3000, function(){
          // This callback function will be executed after the animation is complete
          // Use .stop() to clear any ongoing animations and prevent hide when hovered
          $(this).stop(true, true).hide();
      });
  });
  });


}

function hideToasty()
{
  $("#toast").hide()
}

  
  $(document).ready(() => {
  $("#btn_toastclose").click(()=>{
    $("#toast").fadeOut(500)
  //   $("#toast").animate({ opacity: 0 }, 500, function(){
  //     // This callback function will be executed after the animation is complete
  //     // Use .stop() to clear any ongoing animations and prevent hide when hovered
  //     $(this).stop(true, true).hide();
  // });
  })

  $("#btn_cancle").click(() => {
    if (confirm("Are You Sure ! Want to exit from this page..."))
      window.location.href = "\\";
  });

  // this function create object of table data  and send to server side
  $("#tbl_addProduct").click(() => {
    $(".loader").show();
    

    if (!validPanel("pnl_challan") || !validateTable("tab_logic")  ) {
      showToasty(
        "<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>",
        "bg-danger",
        "Please Fix All Errors",
        "Error"
      );
      return false;
    }

    let obj_product = [];
    let obj_challan={
      challan_no:$('#html_challanno').val(),
      supplier_fid:$('#html_supplier').val(),
      challan_date:$('#html_ChallanDate').val(),
      quantity:$('#html_Quantity').val(),
      amount:$('#html_Amount').val()


    };
    



    $.each($("#tab_logic tr"), (index, row) => {
      // $.each($(row).find('td'),()=>{
      let obj = {};
      let elemWithAttribute = $(row).find("[data-field]");
      $.each(elemWithAttribute, (i, r) => {
        // if(r.type=='select-one')
        // {
        //     var f={};
        //     f[$(r).attr("data-field")]=$(r).val();
        //     obj[$(r).attr("data-field")] =f;

        // }
        // else
        {
          obj[$(r).attr("data-field")] = $(r).val();
        }
      });

      if (Object.keys(obj).length > 0) obj_product.push(obj);

      // })
    });

    const obj={
      desObjProduct:obj_product,
      desObjChallan:obj_challan
      
      
          }

    
    if (obj_product.length > 0) {
      $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/master/saveproduct", // URL of your server-side endpoint for handling products
        data: JSON.stringify(obj), // Convert the array to JSON string
        success: function (response) {
          // Handle the response from the server
          console.log("Data sent successfully:", response);
          window.setTimeout(()=>$(".loader").hide(),2000);
        },
        error: function (error) {
          console.error("Error sending data:", error);
         window.setTimeout(()=>$(".loader").hide(),2000);
        },
      });
    }
  });
  //this function for valid table data \

  function validateTable(id) {
    let flag = true;
    $.each($("#" + id + " tr"), (index, tr) => {
      var mandatoryFields = $(tr).find(".mandatory");
      $.each(mandatoryFields, (i, f) => {
        if ($(f).find("[data-field]").val() == "") {
          // $(f).append('<span class="_error">Field is Mandatory</span>');
          $(f).find("span").text("fields Is Mandatory");

          flag = false;
        } else {
          $(f).find("span").text("");
        }
      });
    });
    return flag;
  }

  function validPanel(id)
  {
    let flag =true;
    $.each($("#" + id).find(".form-floating"), (index, f) => {
     
        if ($(f).find("[data-field]").val() == "") {
          // $(f).append('<span class="_error">Field is Mandatory</span>');
          $(f).find("span").text("fields Is Mandatory");

          flag = false;
        } else {
          $(f).find("span").text("");
        }
      
    });
    return flag;

  }

  //add row function
  $("#add_row").on("click", function () {
    debugger;

    //check validation on fileds
    if (!validateTable("tab_logic")) return;

    // Dynamic Rows Code

    // Get max row id and set new id
    var newid = 0;
    $.each($("#tab_logic tr"), function () {
      if (parseInt($(this).data("id")) > newid) {
        newid = parseInt($(this).data("id"));
      }
    });
    newid++;

    var tr = $("<tr></tr>", {
      id: "addr" + newid,
      "data-id": newid,
    });

    // loop through each td and create new elements with name of newid
    $.each($("#tab_logic tbody tr:nth(0) td"), function () {
      var td;
      var cur_td = $(this);
      var children = cur_td.children();

      // add new td and element if it has a nane

      if ($(this).data("name") !== undefined) {
        td = $("<td></td>", {
          "data-name": $(cur_td).data("name"),
        });

        var c = $(cur_td).find($(children[0]).prop("tagName")).clone().val("");
        c.attr("name", $(cur_td).data("name") + newid);
        c.appendTo($(td));
        td.appendTo($(tr));
      } else {
        td = $("<td></td>", {
          text: $("#tab_logic tr").length,
        }).appendTo($(tr));
      }
    });

    // add delete button and td
    /*
            $("<td></td>").append(
            $("<button class='btn btn-danger glyphicon glyphicon-remove row-remove'></button>")
            .click(function() {
            $(this).closest("tr").remove();
            })
            ).appendTo($(tr));
            */

    // add the new row
    $(tr).appendTo($("#tab_logic"));
    $("#" + tr[0].id)
      .find("input")
      .val("");
    $("#" + tr[0].id)
      .find("._error")
      .text("");

    $(tr)
      .find("td button.row-remove")
      .on("click", function () {
        $(this).closest("tr").remove();
      });
  });
});
