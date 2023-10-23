function showToasty(icon, bgclass, message, stat) {
  debugger;
  $("#toast div strong").prepend(icon);
  $("#toast div strong").text(stat);
  $("#toast .toast-header").addClass(bgclass);
  $(".toast-body").text(message);
  $("#toast").show();
}
$(document).ready(() => {
  debugger;
  $("#btn_cancle").click(() => {
    if (confirm("Are You Sure ! Want to exit from this page..."))
      window.location.href = "\\";
  });
 


  // this function create object of table data  and send to server side 
  $("#tbl_addProduct").click(() => {
    debugger;

if(!validateTable('tab_logic'))
{
  showToasty("<i class='fa-solid fa-triangle-exclamation fa-shake fa-xl text-white'>&nbsp;&nbsp;</i>", "bg-danger", "Please Fix All Errors", "Error")
  return false ;   
}


    let obj_product = [];

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

      if (Object.keys(obj).length > 0) 
      obj_product.push(obj);
      
      // })
    });
    console.log(obj_product);
if(obj_product.length>0)
{
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/master/saveproduct", // URL of your server-side endpoint for handling products
        data: JSON.stringify(obj_product), // Convert the array to JSON string
        success: function(response) {
            // Handle the response from the server
            console.log("Data sent successfully:", response);
        },
        error: function(error) {
            console.error("Error sending data:", error);
        }
    });
}



  });
//this function for valid table data \

function validateTable(id)
{
  let flag=true;
  $.each($('#'+id+' tr'),(index,tr)=>{

    var mandatoryFields=$(tr).find(".mandatory");
    $.each(mandatoryFields,(i,f)=>{
      if($(f).find("[data-field]").val()=='')
      {
       // $(f).append('<span class="_error">Field is Mandatory</span>');
        $(f).find("span").text("fields Is Mandatory");


        flag=false;
      }
      else{
        
        $(f).find("span").text("");
      }


    })
    



  })
  return flag;

}

          //add row function
          $("#add_row").on("click", function () {
            debugger;

            //check validation on fileds 
if(!validateTable('tab_logic'))
return;


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

                var c = $(cur_td)
                  .find($(children[0]).prop("tagName"))
                  .clone()
                  .val("");
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
            $('#'+tr[0].id).find("input").val('')
            $('#'+tr[0].id).find("._error").text('')


            $(tr)
              .find("td button.row-remove")
              .on("click", function () {
                $(this).closest("tr").remove();
              });
          });



});
